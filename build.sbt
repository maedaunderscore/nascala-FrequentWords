// Scalaのバージョンを指定
scalaVersion := "2.10.1"

// 使用するライブラリを追加(依存するライブラリも取ってくる)
libraryDependencies ++= List(
  "org.json4s" %% "json4s-native" % "3.1.0",
  "net.databinder" %% "dispatch-http" % "0.8.9",
  "net.databinder" %% "dispatch-jsoup" % "0.8.9",
  "net.databinder" %% "unfiltered-filter" % "0.6.7",
  "net.databinder" %% "unfiltered-jetty" % "0.6.7",  
  "org.atilika.kuromoji" % "kuromoji" % "0.7.7", 
  "org.scalatest" %% "scalatest" % "1.9.1" % "test",  
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
  "junit" % "junit" % "4.4" % "test",
  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.2",
  "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2"
)
// json4s - JSONを扱うためのライブラリ
// dispatch - HTTP通信をするためのライブラリ
// dispatch-jsoup - jsoup用ラッパー(jsoupはHTMLをJQueryライクに扱うJavaのライブラリ)
// unfiltered - Webサーバー
// kuromoji - 日本語字句解析ライブラリ (Javaのライブラリ)
// scalatest - テストフレームワーク
// scalacheck - ランダムテスト用ライブラリ
// junit - テストフレームワーク（Javaのライブラリ）

// Scalaはバージョン互換がないため、レポジトリの構成が若干異なる。
// 最初の%は
//   Javaのライブラリの場合は、%ひとつ
//   Scalaのライブラリの場合は、%ふたつ

// ※dispatch 0.8.X以前はclassic扱いで、0.9から新APIとして大きく変わってます。
// dispatch-jsoupのscala2.10版が0.8系しかなかったので0.8系にしました

//ライブラリを取得するレポジトリを追加(kuromoji用)
resolvers += "kuromoji repo" at "http://www.atilika.org/nexus/content/repositories/atilika"

// sbtプロンプトでconsoleを実行した時に最初に呼ばれる
initialCommands in console := """import nascala._"""

