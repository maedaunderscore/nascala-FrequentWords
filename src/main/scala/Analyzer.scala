package nascala

/*
 * 形態素解析を行う
 */ 
object Analyzer{
  import org.atilika.kuromoji._
  import scala.collection.JavaConverters._

  def frequentWords(top: Int)(src: String): Seq[Word] = {
    val tokens = tokenize(src)
    val count = wordCount(onlyNoun(tokens))
    def desc[A](t1: Word, t2: Word) = t1.occurrence > t2.occurrence

    count.toSeq.sortWith(desc).take(top)
  }

  case class Token(
    name: String,	// 単語の名前
    kind: String,	// 品詞
    kind2: String	// +αの何か
  )

  val tokenizer = Tokenizer.builder().build()

  def tokenize(src: String): Seq[Token] = 
    for(token <- tokenizer.tokenize(src).asScala) yield {
      val name = token.getSurfaceForm
      val Array(kind, kind2, _@_*) =  token.getAllFeaturesArray		// 素人なので何が取れてるのかちゃんと分かってない
      Token(name, kind, kind2)
    }

  def isNoun(token: Token) = token.kind == "名詞" && 
                             Seq("一般", "固有名詞").contains(token.kind2)
  def isTrivial(token: Token) = token.name.toLowerCase != "coq" && 
                                token.name.matches("[a-zA-Z0-9]{0,3}")
  def onlyNoun(xs: Seq[Token]) = xs.filter(isNoun).filterNot(isTrivial)

  def wordCount(tokens: Seq[Token]): Iterable[Word] = 
    for((name, xs) <- tokens.groupBy(_.name)) yield Word(name, xs.length)
}
