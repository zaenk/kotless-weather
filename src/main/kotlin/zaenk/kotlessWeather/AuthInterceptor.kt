package zaenk.kotlessWeather

import io.kotless.MimeType
import io.kotless.dsl.app.http.RouteKey
import io.kotless.dsl.lang.http.HttpRequestInterceptor
import io.kotless.dsl.model.HttpRequest
import io.kotless.dsl.model.HttpResponse


object AuthInterceptor : HttpRequestInterceptor {
    override val priority = 0

    override fun intercept(
        request: HttpRequest,
        key: RouteKey,
        next: (HttpRequest, RouteKey) -> HttpResponse
    ): HttpResponse {
        return if (isValid(request.headers?.get("Authorization"))) {
            next(request, key)
        } else {
            HttpResponse(
                statusCode = 401,
                headers = hashMapOf(
                    "Content-Type" to MimeType.PLAIN.mimeText,
                    "WWW-Authenticate" to "Basic realm=\"you shall not pass\""
                ),
                body = ""
            )
        }
    }

    private fun isValid(token: List<String>?) = token?.first() == "Basic YWRtaW46YWRtaW4="
}
