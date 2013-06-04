package nascala

/*
 通常WebサーバーはJ2EE コンテナ上にデプロイするようにしますが、
 ここでは構成のシンプルさにこだわって、
 コンソールアプリケーションとしてWebサーバーを起動しています
 
 J2EE コンテナ上で実行する場合は、下記URLのsbtのWeb Pluginを使用するとよいです
 https://github.com/JamesEarlDouglas/xsbt-web-plugin
*/
object Main extends App {
  unfiltered.jetty.Http.anylocal.plan(Server).run ( s =>
    unfiltered.util.Browser.open(s"http://127.0.0.1:${s.port}/")
  )
}

// 単語とその出現回数。
// この型の値を作って、HTMLに変換して返すのがこのアプリの大まかな流れ
// 命名ミス。 case class Occrrence(word: String, time: Int) の方がよかった
case class Word(name: String, occurrence: Int)

object Server extends unfiltered.filter.Plan{
  import unfiltered.request._
  import unfiltered.response._

  import scala.util.{Try, Success, Failure}
  private def main(user: String) = 
    for{
      page <- Try { HatenaDiary.page(user) }
      words <- Try { Analyzer.frequentWords(10)(page) }
    } yield words

  def intent = {
    case Path(Seg("resources":: xs)) => ResponseResource(xs.mkString("/"))
    case Params(User(user)) =>
      Html5(main(user) match {
        case Success(words) => Template.frequentWordPage(user)(words)
        case Failure(ex) => Template.errorPage(ex.getMessage)
      })
    case _ => Html5(Template.mainPage)
  }

  object User extends Params.Extract("user", Params.first)

  case class ResponseResource(filename: String) extends ResponseStreamer{
    import scalax.io.JavaConverters._
    def stream(os: java.io.OutputStream) {
      // src/main/resources/ のファイルを取得
      val is = getClass.getClassLoader.getResourceAsStream(filename)
      is.asInput copyDataTo os.asOutput
    }
  }

  /* パイプライン演算子(|>)は関数適用の記述の順番を入れ替える演算子
     f3(f2(f1(x))) → x |> f1 |> f2 |> f3 と書けるようになる
   */
  implicit class PipeLineOperator[T](x: T){ def |>[S] (f: T => S): S = f(x) }
}
