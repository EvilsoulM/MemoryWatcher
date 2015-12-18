MemoryWatcher
============

A simple watch memory that free memory cache.

How to use
---------------------------------------

1.  In your application class, in `onCreate`, add:

	```java
	 MemoryWatcher.Builder builder = new MemoryWatcher.Builder().setInterval(5 * 1000).setListener(new MemoryListener() {
                @Override
                public void onMemorySize(float size) {
                    Log.i(TAG, "size->" + size);
                    if (size > 100) {//eg 大于100MB内存
                        //TODO clear memory cache
                    }
                }
            }).setUnit(Unit.MB);

            memoryWatcher = builder.build();
            memoryWatcher.start();
	```
