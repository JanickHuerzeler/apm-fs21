public class Consumer {
    private void log(int item, long time) {
        System.out.println("Producing item " + item +
            " took " + time / 1000L + " microseconds");
    }
    public void consume(Producer producer, int count) {
        for (int item = 0; item <= count; ++item) {
            long start = System.nanoTime();
            long result = producer.produce(item);
            long latency = System.nanoTime() - start;
            log(item, latency);
            // Do something with 'result'
        }
    }
    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        consumer.consume(producer, 500); // Run #1
        consumer.consume(producer, 500); // Run #2
    }
}
