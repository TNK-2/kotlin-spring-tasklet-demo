## 実行コマンド

```
java -jar ./build/libs/tasklet-demo-0.0.1-SNAPSHOT.jar --spring.batch.job.names=JobTest2 --spring.batch.job.enabled=true
```

中身の処理についてはとりあえず「JobTestConf」を見ればなんとなくわかるはず。。

## 参考文献
* [JavaConfigでSpring Batchの処理フローを制御する。](https://qiita.com/KevinFQ/items/da521c055b5f153e2cfb)

　　↑良記事
* [[Java][Spring Boot] Spring BatchでTaskletを使ってみる。](https://dev.classmethod.jp/articles/java-spring-boot-batch-tasklet/)
* [春のバッチ - タスクレット対チャンク](https://www.codeflow.site/ja/article/spring-batch-tasklet-chunk)
