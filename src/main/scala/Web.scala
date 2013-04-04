package nascala

/*
 * Web上から情報を取得するためのモジュール
 */ 
object Web{
  import dispatch.classic._
  import dispatch.classic.jsoup.JSoupHttp._
  import org.jsoup.nodes._

  def page(url: String):Document = Http(url.as_jsouped)
  def extract(url: String, selector: String ):String = {
    import scala.collection.JavaConverters._
    page(url).select(selector).asScala.map(_.text).mkString("¥n")
  }
}

object HatenaDiary {
  def page(user: String):String = 
    Web.extract(s"http://d.hatena.ne.jp/$user", "div.body")
}

trait ExtractArticleTag {
  def extract(url: String) = Web.extract(url, "article")
}
object HatenaBlog extends ExtractArticleTag{
  def page(user: String): String = extract(s"http://${user}.hateblo.jp/")
}
