package com.example.submission1androidnouval.data.retrofit
import com.example.submission1androidnouval.data.response.DetailEvent
import com.example.submission1androidnouval.data.response.JustResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

    interface ApiService {
        @GET("/events")
        suspend fun getEvents(@Query("active") active: Int): Response<JustResponse>
        @GET("/events/{id}")
        suspend fun getDetail(@Path("id") id: String): Response<DetailEvent>
        @POST("events/{eventId}/reviews")
        suspend fun postReview(
            @Path("eventId") eventId: String,
            @Query("name") name: String,
            @Query("review") review: String
        ): Response<JustResponse>
    }
