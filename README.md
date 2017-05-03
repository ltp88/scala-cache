# scala-cache

### For simple using:
extends `SimpleLRUTimeEvictCache` and override function `loadData`

``` scala
    override protected def loadData(k: String): String = store(k)
```

### Setting max size:
``` scala
    override protected def maxSize: Long = 1000L
```