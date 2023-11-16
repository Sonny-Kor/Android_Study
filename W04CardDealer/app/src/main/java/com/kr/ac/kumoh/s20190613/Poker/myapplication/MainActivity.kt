package com.kr.ac.kumoh.s20190613.Poker.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kr.ac.kumoh.s20190613.Poker.myapplication.databinding.ActivityMainBinding
import kotlin.math.log
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private lateinit var model : CardDealerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)
        model = ViewModelProvider(this)[CardDealerViewModel::class.java]
        model.cards.observe(this, Observer {
            updateCardImage(it)
            main.textRank.text = getCardRanking(it)
        })
        main.btnShuffle.setOnClickListener {
            model.shuffle()
        }
        main.btnPossibility.setOnClickListener {
            // TODO: 확률 계산 10000 번 실행에 따른 족보별 출현 횟수 및 확률 출력
            val iterations = 100000
            val rankCountArray = HashMap<String,Int>()
            val stringBuilder = StringBuilder()
            for (i : Int in 1 .. iterations ){
                var num = 0;
                val newCards = IntArray(5) { -1 }
                for (i in newCards.indices){
                    do{
                        num = Random.nextInt(52)
                    }while(num in newCards)
                    newCards[i] = num
                }
                newCards.sort()
                // model.shuffle()
                // val simulationCard : IntArray = model.cards.value ?: intArrayOf(-1,-1,-1,-1,-1)
                // Log.d("simulationCard",simulationCard.contentToString())
                val simulationCard : IntArray = newCards
                val rank : String = getCardRanking(simulationCard)
                rankCountArray[rank] = (rankCountArray[rank] ?:0) + 1
            }
            val sortedRankCount = rankCountArray.toList().sortedByDescending { (_, value) -> value }
            Log.d("Probability INFO","==================")
            for ((ranking, count) in sortedRankCount) {
                val probability = (count.toDouble() / iterations) * 100
                stringBuilder.append("$ranking: $count 번 ${String.format("%.6f", probability)} %\n")
                // Log.d("Probability", "$ranking: $count 번 ${String.format("%.6f", probability)} %")
            }
            main.textRank.text = stringBuilder.toString()
            val blankCards = intArrayOf(-1,-1,-1,-1,-1)
            updateCardImage(blankCards)
        }
    }
    private fun updateCardImage(cards: IntArray){
        val res = IntArray(5)
        for (i in res.indices) {
            res[i] = resources.  (
                getCardName(cards[i]),
                "drawable",
                packageName
            )
        }
        main.card1.setImageResource(res[0])
        main.card2.setImageResource(res[1])
        main.card3.setImageResource(res[2])
        main.card4.setImageResource(res[3])
        main.card5.setImageResource(res[4])
    }
    private fun getCardRanking(cards: IntArray) : String{
        // Spade A , Spade 6 , Spade 6 , Diamond 8 , Heart 6 -> ( 0,5,5,20,31)
        // -> numbers = 0, 5, 5, 5, 7
        // -> shapes = {0:3, 1:1, 2:1} Spade 3개, Diamonds 1개, Heart 1개 있다는 의미
        if(cards.contentEquals(intArrayOf(-1,-1,-1,-1,-1)))
            return "카드를 뽑아주세요"
        val numbers = cards.map { it % 13 }.sorted()
        val numbersGroup = numbers.groupingBy { it }.eachCount()
        val shapes = cards.map { it / 13 }.groupingBy { it }.eachCount()
        if (numbers == listOf(0,9,10,11,12) && shapes.size == 1)
            return "로얄 스트레이트 플러시(Royal Straight Flush)"
        if (numbers == listOf(0,2,3,4,5) && shapes.size == 1)
            return "백 스트레이트 플러시(Back Straight Flush)"
        if (numbers.zipWithNext().all { (a,b) -> b - a == 1 } && shapes.size == 1)
            return "스트레이트 플러시(Straight FLush)"
        if (numbersGroup.containsValue(4))
            return "포 카드(Four Card)"
        if (numbersGroup.containsValue(2) && numbersGroup.containsValue(3))
            return "풀 하우스(Full House)"
        if(shapes.size == 1)
            return "플러쉬(Flush)"
        if(numbers == listOf(0,9,10,11,12))
            return "마운틴(Mountain)"
        if(numbers == listOf(0,2,3,4,5))
            return "백 스트레이트(Back Straight)"
        if(numbers.zipWithNext().all { (a,b) -> b - a == 1})
            return "스트레이트(Straight)"
        if(numbersGroup.values.count{it==3} == 1)
            return "트리플(Triple)"
        if(numbersGroup.values.count{it==2} == 2)
            return "투 페어(Two Pair"
        if(numbersGroup.values.count{it==2} == 1)
            return "원 페어(One Pair)"
        return "탑(top)"
    }

    private fun getCardName(c: Int) : String {
        var shape = when (c / 13) {
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }

        val number = when (c % 13) {
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10 -> {
                 shape = shape.plus("2")
                "jack"
            }
            11 -> {
                shape = shape.plus("2")
                "queen"
            }
            12 -> {
                shape = shape.plus("2")
                "king"
            }
            else -> "error"
        }
//        return if(number in arrayOf("jack","queen","king"))
//            "c_${number}_of_${shape}2"
//        else
            return "c_${number}_of_${shape}"
    }


}