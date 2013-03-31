package nascala

object Analyzer{
  import org.atilika.kuromoji._
  import scala.collection.JavaConverters._

  def frequentWords(top: Int)(src: String): Seq[Word] = {
    val tokens = tokenize(src)
    val count = wordCount(onlyNoun(tokens))
    def desc[A](t1: Word, t2: Word) = t1.occurrence > t2.occurrence

    count.toSeq.sortWith(desc).take(top)
  }

  case class Token(name: String, kind: String, kind2: String)

  val tokenizer = Tokenizer.builder().build()

  def tokenize(src: String): Seq[Token] = 
    for(token <- tokenizer.tokenize(src).asScala) yield {
      val name = token.getSurfaceForm
      val Array(kind, kind2, _@_*) =  token.getAllFeaturesArray
      Token(name, kind, kind2)
    }

  def isNoun(token: Token) = token.kind == "名詞" && 
                             Seq("一般", "固有名詞").contains(token.kind2)
  def isTrivial(token: Token) = token.name.matches("[a-zA-Z0-9]{0,3}")
  def onlyNoun(xs: Seq[Token]) = xs.filter(isNoun).filterNot(isTrivial)

  def wordCount(tokens: Seq[Token]): Iterable[Word] = 
    for((name, xs) <- tokens.groupBy(_.name)) yield Word(name, xs.length)
}
