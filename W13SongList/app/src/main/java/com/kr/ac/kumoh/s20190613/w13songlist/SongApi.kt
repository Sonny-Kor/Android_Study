package com.kr.ac.kumoh.s20190613.w13songlist
import retrofit2.http.GET

interface SongApi {
    @GET("song")
    suspend fun getSongs(): List<Song>
}