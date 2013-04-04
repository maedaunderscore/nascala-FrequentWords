/*
REPLで:loadでScalaのコードを読み込む事が可能

scala> :load src/script/Util.scala
Loading src/script/Util.scala...
defined module Util
*/

object Util{
  import scalax.file.Path
  implicit val codec = scalax.io.Codec.UTF8

  val tempResource = Path("target/scala-2.10/classes/temp.html", '/').toAbsolute

  /*
   * この関数で生成したページは
   * http://localhost:(port)/resources/temp.html
   * にアクセスすればみれる
   */ 
  def write(src: scala.xml.Elem){ write(src.toString) }
  def write(src: String){
    tempResource.write(src)
  }
}
