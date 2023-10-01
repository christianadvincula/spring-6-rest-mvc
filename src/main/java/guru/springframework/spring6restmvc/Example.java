package guru.springframework.spring6restmvc;

import lombok.Synchronized;

public class Example {
    private int count = 0;

    @Synchronized
    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        Example example = new Example();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                example.increment();
                System.out.println("thread1: "+example.getCount());
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                example.increment();
                System.out.println("thread2: "+example.getCount()+" ##");
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Count: " + example.getCount());
    }
}
