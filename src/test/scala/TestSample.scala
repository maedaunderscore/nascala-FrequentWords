package nascala

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FreeSpec

/*
 * 普通のユニットテスト
 */ 
class CountingSpec extends FreeSpec with ShouldMatchers{ 
  val tokenSeq = "1,2,3,4,1,2,1,4"
  s"Token Sequence $tokenSeq" - {
    val xs = tokenSeq.split(",").map(Analyzer.Token(_, "", "") )
    val result = Analyzer.wordCount(xs).toSeq

    "should have occurences for each:" - {
      Seq(
        Word("1", 3), 
        Word("2", 2), 
        Word("3", 1), 
        Word("4", 2)
      ).map( w =>
        s"""'${w.name}' occurs ${w.occurrence} times""" in {
          result should contain (w)
        }
      )
    }

    "should have 4 distinct names" in {
      assert(result.map(_.name).toSet.size === 4)
    }
    "should not have a word '5'" in {
      assert(result.filter(_.name == "5")  === Iterable.empty)
    }
  }
}


/*
 * ScalaCheckを使ったランダムテスト
 */ 
import org.scalatest.prop.Checkers
import org.scalatest.junit.JUnitSuite
import org.junit.Test

class CountingByScalaCheck extends Checkers with JUnitSuite {
  import org.scalacheck.Arbitrary
  import org.scalacheck.Arbitrary._
  import org.scalacheck.Prop._

  import nascala.Analyzer._

  /*
   * Tokenは独自に作った型なので、生成方法を定義する
   */
  implicit val anyToken = Arbitrary(for {
    x <- arbitrary[String]
  } yield Token(x, "", ""))

  @Test
  def testNoDuplication(){
    check(
      (tokens: List[Token]) => {
        val result = wordCount(tokens)
        result.map(_.name).toSet.size == result.size
      }
    )
  }

  @Test
  def testTotalCount(){
    check(
      (tokens: List[Token]) => 
        wordCount(tokens).map(_.occurrence).sum == tokens.size
    )
  }

  /*
   * 別の実装をしてみて、それと一致するかを見る
   */
  def wordCount(tokens: Seq[Token]): Iterable[Word] = 
    tokens.foldLeft( Map[String, Int]())( (acc, x) => 
      acc.get(x.name) match {
        case None => acc.updated(x.name, 1)
        case Some(v) => acc.updated(x.name, v + 1)
      }
    ).map({case (name, occurence) =>  Word(name, occurence)})

  @Test
  def testSameResult(){
    check(
      (tokens: List[Token]) => 
      wordCount(tokens).toSet == Analyzer.wordCount(tokens).toSet
    )
  }
}
