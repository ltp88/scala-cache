# scala-cache

### For simple using:
extends `SimpleLRUTimeEvictCache` and override function `loadData`

``` scala
    override protected def loadData(k: K): V = {
        // TODO: Get data code here...
    }
```

### Setting eviction:
``` scala
    override protected def maxSize: Long = 1000L
    
    override protected def expiredAfterWrite: Option[(Long, TimeUnit)] = Some(5L, TimeUnit.MINUTES)
    
    override protected def expiredAfterAccess: Option[(Long, TimeUnit)] = Some(5L, TimeUnit.MINUTES)
```

### Setting refresh
``` scala
    override def refreshAfterWrite: Option[(Long, TimeUnit)] = Some(2L, SECONDS)
```

### Removal listening:
``` scala
    override protected def removal(notification: RemovalNotification[K, V]) = {
        // TODO: handle code here...
    }
```

### Remove: 
``` scala
      protected def remove(k: K)
    
      protected def remove(k: Iterable[K])
    
      protected def removeAll()
```