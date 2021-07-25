package pl.kubisiak.data.tumblr

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.kubisiak.data.dto.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitProvider @Inject constructor()  {

	fun provide(): Retrofit {
		return retrofitInstance
	}

	private val retrofitInstance = createRetrofit()

	private fun createRetrofit(): Retrofit {

		val factory = RuntimeTypeAdapterFactory
			.of(PojoPost::class.java, "type")
			.registerSubtype(PojoPost.Text::class.java, "text")
			.registerSubtype(PojoPost.Answer::class.java, "answer")
			.registerSubtype(PojoPost.Photo::class.java,"photo")
			.registerSubtype(PojoPost.Chat::class.java,"chat")
			.registerSubtype(PojoPost.Quote::class.java,"quote")
			.registerSubtype(PojoPost.Link::class.java,"link")
			.registerSubtype(PojoPost.Audio::class.java,"audio")
			.registerSubtype(PojoPost.Video::class.java,"video")
			.registerSubtype(PojoPost.Postcard::class.java,"postcard")
			.registerSubtype(PojoPost.Unknown::class.java,"unknown")

		val gson = GsonBuilder()
			.registerTypeAdapterFactory(factory)
			.create()

		val retval = Retrofit.Builder()
			.baseUrl("https://api.tumblr.com/v2/")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
			.client(createOkHttpClient())
			.build()

		return retval
	}

	private fun createOkHttpClient(): OkHttpClient {
		val httpClient = OkHttpClient.Builder()

		httpClient.addInterceptor { chain ->
			val original = chain.request()
			val requestBuilder = original.newBuilder()
				//.header("User-Agent", "Szymon-Kubisiak-Browser")
				//.header("Accept", "application/vnd.github.v3+json")
				//.addHeader("Authorization", "Basic: " + basicAuth)

			val request = requestBuilder.build()
			chain.proceed(request)
		}
			//.addInterceptor(ErrorInterceptor(Gson()))
			//.addInterceptor(StubInterceptor())
			.writeTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)

		httpClient.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

		return httpClient.build()
	}



	class Car(var engine: String = "X", var body:String = "regular")

	fun cars() {
		var defaultCar = Car()
		var bigCar = Car(body = "big")
		var alternativeEnginedCar = Car(engine = "A")
		var minsCar = Car(engine = "A", body = "big")
		var sameCarAsMins = Car(body = "big", engine = "A")
	}

}

