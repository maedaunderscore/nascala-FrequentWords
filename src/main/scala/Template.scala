package nascala

import scala.xml._

/*
 * HTML の生成をする
 * cssなどはsrc/main/resources/においてあるものを参照するようにMain.scalaで実装してある
 */
object Template{
  def frequentWordPage(user: String)(words: Seq[Word]) = header(
    <p class="text-left">
      <h2>{user} がよく使う名詞は</h2>
      <dl class="dl-horizontal">{
        words.map(w =>  <dt>{w.name}</dt><dd>{w.occurrence} times</dd>)
      }</dl></p>
  )

  def mainPage = header(
    <p class="lead">はてなダイアリーから最近よく使う名詞を調べます</p>
    <form class="form-signin" action="/">
      <h2 class="form-signin-heading">はてなユーザー名を入れてください</h2>
      <input name="user" type="text" placeholder="who?" class="input-medium search-query" />
      <button type="submit" class="btn">Search</button>
    </form>
  )

  def errorPage(message: String) = header(
    <h2 class="lead">エラーが発生しました。</h2>
    <p class="text-left">{message}</p>
  )

  def header(body: NodeSeq) = <html lang="ja"><head>
    <title>なごやかScalaサンプルページ</title>
    <!-- Le styles -->
    <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.css"/>
    <style type="text/css">
      body {{
        padding-top: 20px;
        padding-bottom: 40px;
      }}

      .container-narrow {{
        margin: 0 auto;
        max-width: 700px;
      }}
      .container-narrow &gt; hr {{
        margin: 30px 0;
      }}

      .jumbotron {{
        margin: 60px 0;
        text-align: center;
      }}
      .jumbotron h1 {{
        font-size: 72px;
        line-height: 1;
      }}
      .jumbotron .btn, .jumbotron input {{
        font-size: 21px;
        padding: 14px 24px;
      }}
      .jumbotron input {{
        width:300px; 
      }}
    </style>
    <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap-responsive.css"/>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/resources/bootstrap/js/html5shiv.js"></script>
    <![endif]-->
  </head>
  <body>
    <div class="container-narrow">
      <div class="masthead">
        <h3 class="muted">なごやかScala</h3>
      </div>
      <hr/>
      <div class="jumbotron">{body}</div>
      <hr/>
      <div class="footer">
        <p>#nascala 2013</p>
      </div>
    </div>
</body></html>
}
