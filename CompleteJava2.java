import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.Flow.*;
import java.lang.ref.*;
import java.lang.invoke.*;
import java.net.*;
import java.net.http.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.text.*;
import java.util.stream.*;
import java.util.function.*;

// ============================================================
// var - LOCAL VARIABLE TYPE INFERENCE (Java 10+)
// ============================================================

class VarDemo {

    public static void varExamples() {
        var name = "Alice";
        var age = 30;
        var pi = 3.14159;
        var list = new ArrayList<String>();
        var map = new HashMap<String, List<Integer>>();
        var numbers = List.of(1, 2, 3, 4, 5);

        for (var item : numbers) {
            System.out.println(item);
        }

        var result = numbers.stream()
                            .filter(n -> n % 2 == 0)
                            .collect(Collectors.toList());

        try (var reader = new BufferedReader(new StringReader("hello"))) {
            var line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// ============================================================
// REGEX - Pattern & Matcher
// ============================================================

class RegexDemo {

    public static void basicMatching() {
        String text = "Hello, my email is alice@example.com and bob@test.org";

        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = emailPattern.matcher(text);

        while (matcher.find()) {
            System.out.println("Found: " + matcher.group());
            System.out.println("Start: " + matcher.start() + ", End: " + matcher.end());
        }

        boolean matches = Pattern.matches("\\d{3}-\\d{4}", "123-4567");
        boolean contains = emailPattern.matcher(text).find();

        String replaced = text.replaceAll("[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}", "[REDACTED]");

        String[] parts = "one,two,,three".split(",");
        String[] partsLimit = "one,two,,three".split(",", -1);
    }

    public static void groups() {
        String date = "Today is 2024-06-15 and tomorrow is 2024-06-16";
        Pattern datePattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");
        Matcher m = datePattern.matcher(date);

        while (m.find()) {
            String full = m.group(0);
            String year = m.group(1);
            String month = m.group(2);
            String day = m.group(3);
            System.out.printf("Full: %s, Year: %s, Month: %s, Day: %s%n", full, year, month, day);
        }
    }

    public static void namedGroups() {
        Pattern p = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})");
        Matcher m = p.matcher("2024-06-15");

        if (m.matches()) {
            System.out.println(m.group("year"));
            System.out.println(m.group("month"));
            System.out.println(m.group("day"));
        }
    }

    public static void lookaroundAndFlags() {
        Pattern caseInsensitive = Pattern.compile("hello", Pattern.CASE_INSENSITIVE);
        Pattern multiline = Pattern.compile("^\\d+", Pattern.MULTILINE);
        Pattern dotAll = Pattern.compile(".*", Pattern.DOTALL);
        Pattern comments = Pattern.compile(
            "\\d{3}   # area code\n" +
            "-        # separator\n" +
            "\\d{4}   # number",
            Pattern.COMMENTS
        );

        String text = "100abc 200def 300ghi";

        Pattern lookAhead = Pattern.compile("\\d+(?=[a-z])");
        Pattern lookBehind = Pattern.compile("(?<=\\d)[a-z]+");
        Pattern negLookAhead = Pattern.compile("\\d+(?![a-z])");
        Pattern negLookBehind = Pattern.compile("(?<!\\d)[a-z]+");

        Matcher m = lookAhead.matcher(text);
        while (m.find()) System.out.println(m.group());
    }

    public static void replaceWithFunction() {
        Pattern p = Pattern.compile("\\b\\w+\\b");
        Matcher m = p.matcher("hello world java");

        String result = m.replaceAll(match -> match.group().toUpperCase());
        System.out.println(result);

        String result2 = m.replaceFirst(match -> "[" + match.group() + "]");
    }

    public static void splitAndStream() {
        Pattern p = Pattern.compile("\\s+");
        String[] words = p.split("  hello   world   java  ");

        Stream<String> stream = p.splitAsStream("one two three four");
        stream.filter(s -> s.length() > 3).forEach(System.out::println);

        Pattern p2 = Pattern.compile("[aeiou]");
        boolean hasVowel = p2.asPredicate().test("hello");
        boolean isAllVowel = p2.asMatchPredicate().test("aeiou");
    }
}

// ============================================================
// IMUTABLE COLLECTIONS (Java 9+)
// ============================================================

class ImmutableCollectionsDemo {

    public static void listOf() {
        List<String> list = List.of("a", "b", "c");
        List<Integer> empty = List.of();
        List<String> single = List.of("only");

        list.size();
        list.get(0);
        list.contains("b");
        list.indexOf("b");
        list.stream().collect(Collectors.toList());

        List<String> copyOf = List.copyOf(new ArrayList<>(list));
    }

    public static void setOf() {
        Set<String> set = Set.of("x", "y", "z");
        Set<Integer> empty = Set.of();

        set.contains("x");
        set.size();

        Set<String> copyOf = Set.copyOf(new HashSet<>(set));
    }

    public static void mapOf() {
        Map<String, Integer> map = Map.of(
            "one", 1,
            "two", 2,
            "three", 3
        );

        Map<String, Integer> mapEntries = Map.ofEntries(
            Map.entry("a", 1),
            Map.entry("b", 2),
            Map.entry("c", 3)
        );

        map.get("one");
        map.getOrDefault("four", 0);
        map.containsKey("two");
        map.containsValue(3);
        map.size();

        Map<String, Integer> copyOf = Map.copyOf(new HashMap<>(map));
    }
}

// ============================================================
// COLEÇÕES ESPECIAIS
// ============================================================

class SpecialCollectionsDemo {

    enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

    public static void enumMapDemo() {
        EnumMap<Day, String> schedule = new EnumMap<>(Day.class);
        schedule.put(Day.MON, "Work");
        schedule.put(Day.SAT, "Rest");
        schedule.put(Day.SUN, "Rest");

        schedule.containsKey(Day.FRI);
        schedule.getOrDefault(Day.TUE, "Work");
        schedule.forEach((day, task) -> System.out.println(day + ": " + task));
    }

    public static void enumSetDemo() {
        EnumSet<Day> weekend = EnumSet.of(Day.SAT, Day.SUN);
        EnumSet<Day> weekdays = EnumSet.complementOf(weekend);
        EnumSet<Day> all = EnumSet.allOf(Day.class);
        EnumSet<Day> none = EnumSet.noneOf(Day.class);
        EnumSet<Day> range = EnumSet.range(Day.MON, Day.FRI);
        EnumSet<Day> copy = EnumSet.copyOf(weekend);

        weekend.contains(Day.SAT);
        weekend.add(Day.FRI);
        weekend.remove(Day.FRI);
    }

    public static void weakHashMapDemo() {
        WeakHashMap<Object, String> whm = new WeakHashMap<>();
        Object key1 = new Object();
        Object key2 = new Object();

        whm.put(key1, "value1");
        whm.put(key2, "value2");

        key1 = null;
        System.gc();

        System.out.println("Size after GC: " + whm.size());
    }

    public static void identityHashMapDemo() {
        IdentityHashMap<String, Integer> ihm = new IdentityHashMap<>();
        String a = new String("key");
        String b = new String("key");

        ihm.put(a, 1);
        ihm.put(b, 2);

        System.out.println(ihm.size());
        System.out.println(ihm.get(a));
        System.out.println(ihm.get(b));
    }

    public static void bitSetDemo() {
        BitSet bs = new BitSet(64);
        bs.set(0);
        bs.set(5);
        bs.set(10, 15);
        bs.get(5);
        bs.clear(5);
        bs.flip(0);
        bs.flip(0, 10);
        bs.cardinality();
        bs.length();
        bs.size();
        bs.isEmpty();
        bs.nextSetBit(0);
        bs.nextClearBit(0);
        bs.previousSetBit(20);

        BitSet bs2 = new BitSet();
        bs2.set(3);
        bs2.set(5);

        bs.and(bs2);
        bs.or(bs2);
        bs.xor(bs2);
        bs.andNot(bs2);
        bs.intersects(bs2);

        byte[] bytes = bs.toByteArray();
        long[] longs = bs.toLongArray();
        BitSet fromBytes = BitSet.valueOf(bytes);

        bs.stream().forEach(System.out::println);
        bs.toString();
    }

    public static void linkedHashSetDemo() {
        LinkedHashSet<String> lhs = new LinkedHashSet<>();
        lhs.add("banana");
        lhs.add("apple");
        lhs.add("cherry");
        lhs.add("apple");

        lhs.forEach(System.out::println);
    }

    public static void arrayDequeDemo() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        dq.push(1);
        dq.push(2);
        dq.push(3);
        dq.pop();
        dq.peek();
        dq.offer(4);
        dq.offerFirst(0);
        dq.offerLast(5);
        dq.pollFirst();
        dq.pollLast();
        dq.peekFirst();
        dq.peekLast();
        dq.descendingIterator();
        dq.toArray();
    }
}

// ============================================================
// StringJoiner & Scanner & Formatter
// ============================================================

class StringUtilsDemo {

    public static void stringJoinerDemo() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        sj.add("Alice");
        sj.add("Bob");
        sj.add("Charlie");
        sj.toString();
        sj.length();

        StringJoiner sj2 = new StringJoiner(" | ");
        sj2.setEmptyValue("(empty)");
        sj2.merge(sj);
    }

    public static void scannerDemo() {
        Scanner sc = new Scanner(System.in);
        sc.hasNextLine();
        sc.nextLine();
        sc.hasNextInt();
        sc.nextInt();
        sc.hasNextDouble();
        sc.nextDouble();
        sc.hasNext();
        sc.next();
        sc.useDelimiter(",");
        sc.close();

        Scanner fromString = new Scanner("42 3.14 hello");
        int i = fromString.nextInt();
        double d = fromString.nextDouble();
        String s = fromString.next();
        fromString.close();

        Scanner fromFile = null;
        try {
            fromFile = new Scanner(new File("data.txt"));
            while (fromFile.hasNextLine()) {
                System.out.println(fromFile.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fromFile != null) fromFile.close();
        }
    }

    public static void formatterDemo() {
        Formatter fmt = new Formatter();
        fmt.format("Name: %s%n", "Alice");
        fmt.format("Age: %d%n", 30);
        fmt.format("Pi: %.4f%n", Math.PI);
        fmt.format("Hex: %x%n", 255);
        fmt.format("Oct: %o%n", 8);
        fmt.format("Sci: %e%n", 123456.789);
        fmt.format("Bool: %b%n", true);
        fmt.format("Char: %c%n", 'A');
        fmt.format("%-10s|%10s%n", "left", "right");
        fmt.format("%+d%n", 42);
        fmt.format("%05d%n", 42);
        fmt.toString();
        fmt.close();

        String s = String.format("%1$s %2$s %1$s", "ping", "pong");
        System.out.printf("%-20s %5.2f%n", "price:", 9.99);
    }

    public static void localeAndNumberFormat() {
        Locale ptBR = new Locale("pt", "BR");
        Locale usLocale = Locale.US;
        Locale japan = Locale.JAPAN;

        NumberFormat nf = NumberFormat.getInstance(ptBR);
        nf.format(1234567.89);

        NumberFormat currency = NumberFormat.getCurrencyInstance(ptBR);
        currency.format(9.99);

        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.format(0.75);

        NumberFormat compact = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        compact.format(1_000_000);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.format(1234567.891);

        try {
            Number parsed = nf.parse("1.234,56");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void dateTimeFormatterAdvanced() {
        DateTimeFormatter iso = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DateTimeFormatter custom = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter localized = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("pt", "BR"));

        LocalDate date = LocalDate.now();
        String formatted = date.format(custom);
        LocalDate parsed = LocalDate.parse("15/06/2024", custom);

        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder()
            .appendPattern("yyyy")
            .appendLiteral('-')
            .appendPattern("MM")
            .appendLiteral('-')
            .appendPattern("dd")
            .optionalStart()
            .appendLiteral('T')
            .appendPattern("HH:mm:ss")
            .optionalEnd()
            .parseCaseInsensitive()
            .parseLenient();

        DateTimeFormatter built = builder.toFormatter();
    }
}

// ============================================================
// REFERENCES (Weak, Soft, Phantom)
// ============================================================

class ReferencesDemo {

    public static void weakReferenceDemo() {
        Object obj = new Object();
        WeakReference<Object> weakRef = new WeakReference<>(obj);

        Object retrieved = weakRef.get();
        if (retrieved != null) {
            System.out.println("Still alive: " + retrieved);
        }

        obj = null;
        System.gc();

        if (weakRef.get() == null) {
            System.out.println("Garbage collected");
        }

        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        WeakReference<Object> tracked = new WeakReference<>(new Object(), queue);
    }

    public static void softReferenceDemo() {
        byte[] largeData = new byte[1024 * 1024];
        SoftReference<byte[]> softRef = new SoftReference<>(largeData);

        largeData = null;

        byte[] data = softRef.get();
        if (data != null) {
            System.out.println("Data still available");
        } else {
            System.out.println("Data was cleared due to memory pressure");
        }
    }

    public static void phantomReferenceDemo() {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Object obj = new Object();
        PhantomReference<Object> phantomRef = new PhantomReference<>(obj, queue);

        obj = null;
        System.gc();

        phantomRef.get();
        phantomRef.isEnqueued();
        phantomRef.refersTo(null);
    }

    public static void cleanerDemo() {
        java.lang.ref.Cleaner cleaner = java.lang.ref.Cleaner.create();

        class Resource {
            private final java.lang.ref.Cleaner.Cleanable cleanable;

            Resource() {
                cleanable = cleaner.register(this, () -> System.out.println("Cleaning up!"));
            }

            void close() {
                cleanable.clean();
            }
        }

        Resource r = new Resource();
        r.close();
    }
}

// ============================================================
// ThreadLocal & InheritableThreadLocal
// ============================================================

class ThreadLocalDemo {

    private static final ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> (int)(Math.random() * 1000));
    private static final ThreadLocal<SimpleDateFormat> dateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    private static final InheritableThreadLocal<String> context = new InheritableThreadLocal<>();

    public static void demo() throws InterruptedException {
        context.set("parent-value");

        Thread child = new Thread(() -> {
            System.out.println("Child sees: " + context.get());
            System.out.println("Thread ID: " + threadId.get());
        });
        child.start();
        child.join();

        Thread t1 = new Thread(() -> {
            System.out.println("T1 ID: " + threadId.get());
            threadId.set(1);
            System.out.println("T1 ID after set: " + threadId.get());
            threadId.remove();
        });

        Thread t2 = new Thread(() -> {
            System.out.println("T2 ID: " + threadId.get());
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

// ============================================================
// ForkJoin
// ============================================================

class ForkJoinDemo {

    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 1000;
        private final long[] array;
        private final int start;
        private final int end;

        SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) sum += array[i];
                return sum;
            }

            int mid = (start + end) / 2;
            SumTask left = new SumTask(array, start, mid);
            SumTask right = new SumTask(array, mid, end);

            left.fork();
            long rightResult = right.compute();
            long leftResult = left.join();

            return leftResult + rightResult;
        }
    }

    static class PrintTask extends RecursiveAction {
        private final String[] words;
        private final int start;
        private final int end;

        PrintTask(String[] words, int start, int end) {
            this.words = words;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= 3) {
                for (int i = start; i < end; i++) System.out.println(words[i]);
                return;
            }

            int mid = (start + end) / 2;
            invokeAll(
                new PrintTask(words, start, mid),
                new PrintTask(words, mid, end)
            );
        }
    }

    public static void demo() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinPool custom = new ForkJoinPool(4);

        long[] array = LongStream.rangeClosed(1, 1_000_000).toArray();
        SumTask task = new SumTask(array, 0, array.length);

        long sum = pool.invoke(task);
        System.out.println("Sum: " + sum);

        pool.submit(task);
        pool.execute(task);
        pool.getParallelism();
        pool.getPoolSize();
        pool.getActiveThreadCount();
        pool.getQueuedTaskCount();

        custom.shutdown();
    }
}

// ============================================================
// StampedLock, Phaser, Exchanger
// ============================================================

class AdvancedConcurrencyDemo {

    private int value = 0;
    private final StampedLock stampedLock = new StampedLock();

    public void stampedLockDemo() {
        long stamp = stampedLock.writeLock();
        try {
            value++;
        } finally {
            stampedLock.unlockWrite(stamp);
        }

        long readStamp = stampedLock.readLock();
        try {
            System.out.println(value);
        } finally {
            stampedLock.unlockRead(readStamp);
        }

        long optimisticStamp = stampedLock.tryOptimisticRead();
        int localValue = value;
        if (!stampedLock.validate(optimisticStamp)) {
            long fallbackStamp = stampedLock.readLock();
            try {
                localValue = value;
            } finally {
                stampedLock.unlockRead(fallbackStamp);
            }
        }

        long stamp2 = stampedLock.readLock();
        try {
            long writeStamp = stampedLock.tryConvertToWriteLock(stamp2);
            if (writeStamp != 0L) {
                stamp2 = writeStamp;
                value++;
            } else {
                stampedLock.unlockRead(stamp2);
                stamp2 = stampedLock.writeLock();
                value++;
            }
        } finally {
            stampedLock.unlock(stamp2);
        }
    }

    public static void phaserDemo() throws InterruptedException {
        Phaser phaser = new Phaser(1);

        for (int i = 0; i < 3; i++) {
            phaser.register();
            new Thread(() -> {
                System.out.println("Phase 1 work");
                phaser.arriveAndAwaitAdvance();
                System.out.println("Phase 2 work");
                phaser.arriveAndDeregister();
            }).start();
        }

        phaser.arriveAndAwaitAdvance();
        System.out.println("All done phase 1");
        phaser.arriveAndDeregister();

        Phaser tiered = new Phaser(new Phaser(), 0);
        tiered.getPhase();
        tiered.getRegisteredParties();
        tiered.getArrivedParties();
        tiered.getUnarrivedParties();
        tiered.isTerminated();
    }

    public static void exchangerDemo() throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread producer = new Thread(() -> {
            try {
                String produced = "data from producer";
                String received = exchanger.exchange(produced);
                System.out.println("Producer received: " + received);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                String received = exchanger.exchange("ack from consumer");
                System.out.println("Consumer received: " + received);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

    public static void transferQueueDemo() throws InterruptedException {
        TransferQueue<Integer> tq = new LinkedTransferQueue<>();

        Thread producer = new Thread(() -> {
            try {
                tq.transfer(42);
                System.out.println("Transferred!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Took: " + tq.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}

// ============================================================
// Flow API - Reactive Streams (Java 9+)
// ============================================================

class FlowDemo {

    static class SimplePublisher<T> implements Publisher<T> {
        private final List<T> items;

        SimplePublisher(List<T> items) {
            this.items = items;
        }

        @Override
        public void subscribe(Subscriber<? super T> subscriber) {
            subscriber.onSubscribe(new Subscription() {
                private int index = 0;
                private boolean cancelled = false;

                @Override
                public void request(long n) {
                    long count = 0;
                    while (!cancelled && index < items.size() && count < n) {
                        subscriber.onNext(items.get(index++));
                        count++;
                    }
                    if (!cancelled && index >= items.size()) {
                        subscriber.onComplete();
                    }
                }

                @Override
                public void cancel() {
                    cancelled = true;
                }
            });
        }
    }

    static class PrintSubscriber<T> implements Subscriber<T> {
        private Subscription subscription;

        @Override
        public void onSubscribe(Subscription s) {
            this.subscription = s;
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(T item) {
            System.out.println("Received: " + item);
        }

        @Override
        public void onError(Throwable t) {
            System.err.println("Error: " + t.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("Done!");
        }
    }

    static class DoubleProcessor extends SubmissionPublisher<Integer> implements Processor<Integer, Integer> {
        private Subscription subscription;

        @Override
        public void onSubscribe(Subscription s) {
            this.subscription = s;
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(Integer item) {
            submit(item * 2);
        }

        @Override
        public void onError(Throwable t) {
            closeExceptionally(t);
        }

        @Override
        public void onComplete() {
            close();
        }
    }

    public static void demo() throws InterruptedException {
        SimplePublisher<Integer> publisher = new SimplePublisher<>(List.of(1, 2, 3, 4, 5));
        PrintSubscriber<Integer> subscriber = new PrintSubscriber<>();
        publisher.subscribe(subscriber);

        try (SubmissionPublisher<String> sp = new SubmissionPublisher<>()) {
            sp.subscribe(new PrintSubscriber<>());
            sp.submit("Hello");
            sp.submit("World");
            Thread.sleep(100);
        }
    }
}

// ============================================================
// ProcessBuilder & Runtime & System
// ============================================================

class SystemDemo {

    public static void processBuilderDemo() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("echo", "Hello from process");
        pb.directory(new File(System.getProperty("user.home")));
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        pb.environment().put("MY_VAR", "value");
        pb.redirectErrorStream(true);

        Process process = pb.start();
        int exitCode = process.waitFor();
        process.exitValue();
        process.isAlive();
        process.pid();
        process.destroy();
        process.destroyForcibly();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            reader.lines().forEach(System.out::println);
        }

        ProcessHandle.current().pid();
        ProcessHandle.current().info().command();
        ProcessHandle.current().info().startInstant();
        ProcessHandle.allProcesses().limit(5).forEach(p -> System.out.println(p.pid()));
    }

    public static void runtimeDemo() {
        Runtime runtime = Runtime.getRuntime();
        runtime.availableProcessors();
        runtime.totalMemory();
        runtime.freeMemory();
        runtime.maxMemory();
        runtime.gc();

        runtime.addShutdownHook(new Thread(() -> System.out.println("Shutdown hook!")));

        try {
            Process p = runtime.exec("echo hello");
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void systemDemo() {
        System.currentTimeMillis();
        System.nanoTime();
        System.gc();
        System.lineSeparator();
        System.getenv("PATH");
        System.getenv();
        System.getProperty("java.version");
        System.getProperty("os.name");
        System.getProperty("user.home");
        System.getProperty("user.dir");
        System.getProperty("java.class.path");
        System.getProperties();
        System.setProperty("my.key", "my.value");
        System.identityHashCode(new Object());

        int[] src = {1, 2, 3, 4, 5};
        int[] dst = new int[5];
        System.arraycopy(src, 0, dst, 0, 5);

        System.out.println("stdout");
        System.err.println("stderr");

        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        System.out.println("redirected");
        System.setOut(oldOut);
    }
}

// ============================================================
// NETWORKING
// ============================================================

class NetworkingDemo {

    public static void socketDemo() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            serverSocket.setSoTimeout(5000);
            serverSocket.setReuseAddress(true);

            System.out.println("Waiting for connection...");
            try (Socket client = serverSocket.accept()) {
                client.getInetAddress();
                client.getPort();
                client.getLocalPort();
                client.isConnected();
                client.isClosed();
                client.setSoTimeout(3000);
                client.setTcpNoDelay(true);
                client.setKeepAlive(true);

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                String line = in.readLine();
                out.println("Echo: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clientSocketDemo() throws IOException {
        try (Socket socket = new Socket("localhost", 8080)) {
            socket.setSoTimeout(3000);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Hello Server");
            String response = in.readLine();
            System.out.println("Server: " + response);
        }
    }

    public static void httpClientDemo() throws Exception {
        HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

        HttpRequest getRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://httpbin.org/get"))
            .header("Accept", "application/json")
            .header("User-Agent", "Java HttpClient")
            .timeout(Duration.ofSeconds(30))
            .GET()
            .build();

        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        response.statusCode();
        response.headers().map();
        response.body();
        response.uri();
        response.version();

        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://httpbin.org/post"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"key\": \"value\"}"))
            .build();

        CompletableFuture<HttpResponse<String>> asyncResponse = client.sendAsync(
            getRequest,
            HttpResponse.BodyHandlers.ofString()
        );
        asyncResponse.thenApply(HttpResponse::body).thenAccept(System.out::println).join();

        HttpResponse<byte[]> bytesResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofByteArray());
        HttpResponse<Void> discardResponse = client.send(getRequest, HttpResponse.BodyHandlers.discarding());
        HttpResponse<Path> fileResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofFile(Path.of("/tmp/response.json")));
        HttpResponse<Stream<String>> linesResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofLines());
    }

    public static void urlDemo() throws Exception {
        URL url = new URL("https://example.com/path?query=value#fragment");
        url.getProtocol();
        url.getHost();
        url.getPort();
        url.getPath();
        url.getQuery();
        url.getRef();
        url.toURI();

        URI uri = new URI("https", "example.com", "/path", "query=value", "fragment");
        uri.getScheme();
        uri.getHost();
        uri.getPath();
        uri.normalize();
        uri.resolve("/other");
        uri.toURL();

        InetAddress addr = InetAddress.getByName("localhost");
        addr.getHostAddress();
        addr.getHostName();
        addr.isLoopbackAddress();
        addr.isReachable(3000);
        InetAddress.getLocalHost();
        InetAddress.getAllByName("google.com");
    }
}

// ============================================================
// StackWalker (Java 9+)
// ============================================================

class StackWalkerDemo {

    public static void demo() {
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

        walker.walk(frames -> frames
            .filter(f -> !f.getClassName().startsWith("java"))
            .collect(Collectors.toList())
        ).forEach(f -> System.out.println(f.getClassName() + "." + f.getMethodName() + ":" + f.getLineNumber()));

        Optional<StackWalker.StackFrame> caller = walker.walk(frames ->
            frames.filter(f -> f.getMethodName().startsWith("demo"))
                  .findFirst()
        );

        walker.forEach(frame -> {
            frame.getClassName();
            frame.getMethodName();
            frame.getLineNumber();
            frame.getDeclaringClass();
            frame.getDescriptor();
            frame.getByteCodeIndex();
        });

        StackWalker.getInstance().getCallerClass();
    }
}

// ============================================================
// ENUM COM MÉTODO ABSTRATO
// ============================================================

enum Operation {
    ADD("+") {
        @Override
        public double apply(double x, double y) { return x + y; }
    },
    SUBTRACT("-") {
        @Override
        public double apply(double x, double y) { return x - y; }
    },
    MULTIPLY("*") {
        @Override
        public double apply(double x, double y) { return x * y; }
    },
    DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            if (y == 0) throw new ArithmeticException("Division by zero");
            return x / y;
        }
    };

    private final String symbol;

    Operation(String symbol) { this.symbol = symbol; }

    public abstract double apply(double x, double y);

    public String getSymbol() { return symbol; }

    public static Optional<Operation> fromSymbol(String sym) {
        return Arrays.stream(values())
                     .filter(op -> op.symbol.equals(sym))
                     .findFirst();
    }

    @Override
    public String toString() {
        return symbol;
    }
}

// ============================================================
// INTERFACE COM MÉTODO PRIVATE (Java 9+)
// ============================================================

interface Logger {
    void log(String message);
    void logError(String message);

    default void logInfo(String message) {
        log(formatMessage("INFO", message));
    }

    default void logWarning(String message) {
        log(formatMessage("WARN", message));
    }

    default void logException(Exception e) {
        logError(formatMessage("ERROR", e.getMessage()));
        logStackTrace(e);
    }

    private String formatMessage(String level, String message) {
        return String.format("[%s] %s: %s", LocalDateTime.now(), level, message);
    }

    private void logStackTrace(Exception e) {
        Arrays.stream(e.getStackTrace())
              .map(StackTraceElement::toString)
              .forEach(this::logError);
    }

    static Logger console() {
        return new Logger() {
            @Override
            public void log(String message) { System.out.println(message); }
            @Override
            public void logError(String message) { System.err.println(message); }
        };
    }
}

// ============================================================
// COVARIANT RETURN TYPE
// ============================================================

class Animal {
    private String name;

    public Animal setName(String name) {
        this.name = name;
        return this;
    }

    public Animal clone() {
        Animal a = new Animal();
        a.name = this.name;
        return a;
    }

    public String getName() { return name; }
}

class Dog extends Animal {
    private String breed;

    @Override
    public Dog setName(String name) {
        super.setName(name);
        return this;
    }

    public Dog setBreed(String breed) {
        this.breed = breed;
        return this;
    }

    @Override
    public Dog clone() {
        Dog d = new Dog();
        d.setName(this.getName());
        d.breed = this.breed;
        return d;
    }

    public String getBreed() { return breed; }

    @Override
    public String toString() {
        return "Dog[" + getName() + ", " + breed + "]";
    }
}

// ============================================================
// VarHandle (Java 9+)
// ============================================================

class VarHandleDemo {

    private int x = 10;
    private volatile int y = 0;
    private static int staticField = 42;

    private static final VarHandle X_HANDLE;
    private static final VarHandle STATIC_HANDLE;

    static {
        try {
            X_HANDLE = MethodHandles.lookup().findVarHandle(VarHandleDemo.class, "x", int.class);
            STATIC_HANDLE = MethodHandles.lookup().findStaticVarHandle(VarHandleDemo.class, "staticField", int.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void demo() throws ReflectiveOperationException {
        VarHandleDemo obj = new VarHandleDemo();

        int val = (int) X_HANDLE.get(obj);
        X_HANDLE.set(obj, 20);
        X_HANDLE.setVolatile(obj, 30);
        int old = (int) X_HANDLE.getAndSet(obj, 40);
        boolean swapped = X_HANDLE.compareAndSet(obj, 40, 50);
        int prev = (int) X_HANDLE.getAndAdd(obj, 5);
        int next = (int) X_HANDLE.addAndGet(obj, 3);

        X_HANDLE.setOpaque(obj, 10);
        X_HANDLE.setRelease(obj, 11);
        int acquired = (int) X_HANDLE.getAcquire(obj);

        STATIC_HANDLE.set(100);
        int sv = (int) STATIC_HANDLE.get();

        VarHandle arrayHandle = MethodHandles.arrayElementVarHandle(int[].class);
        int[] array = {1, 2, 3};
        int elem = (int) arrayHandle.get(array, 0);
        arrayHandle.set(array, 0, 99);
        arrayHandle.compareAndSet(array, 0, 99, 50);
    }
}

// ============================================================
// MethodHandles (java.lang.invoke)
// ============================================================

class MethodHandlesDemo {

    public static int add(int a, int b) { return a + b; }
    public String greet(String name) { return "Hello, " + name + "!"; }

    public static void demo() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle addHandle = lookup.findStatic(
            MethodHandlesDemo.class,
            "add",
            MethodType.methodType(int.class, int.class, int.class)
        );
        int result = (int) addHandle.invoke(3, 4);
        int result2 = (int) addHandle.invokeExact(3, 4);

        MethodHandle greetHandle = lookup.findVirtual(
            MethodHandlesDemo.class,
            "greet",
            MethodType.methodType(String.class, String.class)
        );
        String greeting = (String) greetHandle.invoke(new MethodHandlesDemo(), "World");

        MethodHandle constructor = lookup.findConstructor(
            MethodHandlesDemo.class,
            MethodType.methodType(void.class)
        );

        MethodHandle adder = addHandle.bindTo(null);
        MethodHandle bound = greetHandle.bindTo(new MethodHandlesDemo());
        String g = (String) bound.invoke("Alice");

        MethodHandle asType = addHandle.asType(MethodType.methodType(long.class, long.class, long.class));
        MethodHandle spreader = addHandle.asSpreader(int[].class, 2);
        MethodHandle collector = addHandle.asCollector(int[].class, 2);

        MethodHandles.filterArguments(addHandle, 0, MethodHandles.identity(int.class));
        MethodHandles.foldArguments(addHandle, MethodHandles.identity(int.class));
        MethodHandles.guardWithTest(
            lookup.findStatic(Objects.class, "isNull", MethodType.methodType(boolean.class, Object.class)),
            MethodHandles.constant(String.class, "null"),
            bound
        );
    }
}

// ============================================================
// COERCIONS AVANÇADAS & TYPE SYSTEM
// ============================================================

class TypeSystemDemo {

    @SuppressWarnings("unchecked")
    public static void genericsTypeErasure() {
        List<String> strings = new ArrayList<>();
        List<Integer> ints = new ArrayList<>();

        System.out.println(strings.getClass() == ints.getClass());

        List rawList = strings;
        rawList.add(42);

        List<String> unchecked = (List<String>) rawList;
    }

    public static void covarianceContravariance() {
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List<? extends Number> covariant = ints;
        Number n = covariant.get(0);

        List<Number> numbers = new ArrayList<>();
        List<? super Integer> contravariant = numbers;
        contravariant.add(42);

        Number[] numArray = new Integer[3];
        numArray[0] = 1;
    }

    public static void castingDemo() {
        Object obj = "hello";
        if (obj instanceof String s) {
            System.out.println(s.toUpperCase());
        }

        Number num = 42;
        int i = (int)(Integer) num;

        double d = (double)(int)(Integer) num;

        List<?> unknown = new ArrayList<String>();
        List<String> known = (List<String>) unknown;

        int[] primitiveArr = {1, 2, 3};
        Object boxed = primitiveArr;
        int[] back = (int[]) boxed;
    }
}

// ============================================================
// SEALED INTERFACE AVANÇADO (Java 17+)
// ============================================================

sealed interface Result<T> permits Result.Success, Result.Failure {

    record Success<T>(T value) implements Result<T> {}
    record Failure<T>(String error, Throwable cause) implements Result<T> {
        Failure(String error) { this(error, null); }
    }

    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    default T getOrElse(T defaultValue) {
        return switch (this) {
            case Success<T> s -> s.value();
            case Failure<T> f -> defaultValue;
        };
    }

    default <U> Result<U> map(Function<T, U> f) {
        return switch (this) {
            case Success<T> s -> {
                try {
                    yield new Success<>(f.apply(s.value()));
                } catch (Exception e) {
                    yield new Failure<>(e.getMessage(), e);
                }
            }
            case Failure<T> f -> new Failure<>(f.error(), f.cause());
        };
    }

    static <T> Result<T> of(Supplier<T> supplier) {
        try {
            return new Success<>(supplier.get());
        } catch (Exception e) {
            return new Failure<>(e.getMessage(), e);
        }
    }
}

// ============================================================
// RECORD AVANÇADO
// ============================================================

record Range(int min, int max) {
    Range {
        if (min > max) throw new IllegalArgumentException("min > max");
    }

    boolean contains(int value) {
        return value >= min && value <= max;
    }

    boolean overlaps(Range other) {
        return this.min <= other.max && other.min <= this.max;
    }

    int size() {
        return max - min + 1;
    }

    IntStream stream() {
        return IntStream.rangeClosed(min, max);
    }

    Range union(Range other) {
        return new Range(Math.min(min, other.min), Math.max(max, other.max));
    }

    Optional<Range> intersection(Range other) {
        int newMin = Math.max(min, other.min);
        int newMax = Math.min(max, other.max);
        return newMin <= newMax ? Optional.of(new Range(newMin, newMax)) : Optional.empty();
    }
}

// ============================================================
// INTERFACE FLUENTE (Builder Pattern)
// ============================================================

class QueryBuilder {
    private String table;
    private final List<String> columns = new ArrayList<>();
    private final List<String> conditions = new ArrayList<>();
    private String orderBy;
    private boolean descending = false;
    private Integer limit;
    private Integer offset;

    private QueryBuilder() {}

    public static QueryBuilder from(String table) {
        QueryBuilder qb = new QueryBuilder();
        qb.table = table;
        return qb;
    }

    public QueryBuilder select(String... cols) {
        columns.addAll(Arrays.asList(cols));
        return this;
    }

    public QueryBuilder where(String condition) {
        conditions.add(condition);
        return this;
    }

    public QueryBuilder orderBy(String column) {
        this.orderBy = column;
        return this;
    }

    public QueryBuilder desc() {
        this.descending = true;
        return this;
    }

    public QueryBuilder limit(int n) {
        this.limit = n;
        return this;
    }

    public QueryBuilder offset(int n) {
        this.offset = n;
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(columns.isEmpty() ? "*" : String.join(", ", columns));
        sb.append(" FROM ").append(table);
        if (!conditions.isEmpty()) sb.append(" WHERE ").append(String.join(" AND ", conditions));
        if (orderBy != null) sb.append(" ORDER BY ").append(orderBy).append(descending ? " DESC" : " ASC");
        if (limit != null) sb.append(" LIMIT ").append(limit);
        if (offset != null) sb.append(" OFFSET ").append(offset);
        return sb.toString();
    }
}

// ============================================================
// STRATEGY, OBSERVER, DECORATOR PATTERNS
// ============================================================

interface SortStrategy<T> {
    void sort(List<T> list, Comparator<T> comparator);
}

class BubbleSort<T> implements SortStrategy<T> {
    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}

interface EventListener<T> {
    void onEvent(T event);
}

class EventBus<T> {
    private final Map<String, List<EventListener<T>>> listeners = new HashMap<>();

    public void subscribe(String event, EventListener<T> listener) {
        listeners.computeIfAbsent(event, k -> new ArrayList<>()).add(listener);
    }

    public void unsubscribe(String event, EventListener<T> listener) {
        listeners.getOrDefault(event, List.of()).remove(listener);
    }

    public void publish(String event, T payload) {
        listeners.getOrDefault(event, List.of()).forEach(l -> l.onEvent(payload));
    }
}

interface TextProcessor {
    String process(String text);

    default TextProcessor andThen(TextProcessor next) {
        return text -> next.process(this.process(text));
    }
}

class TrimProcessor implements TextProcessor {
    @Override
    public String process(String text) { return text.trim(); }
}

class UpperCaseProcessor implements TextProcessor {
    @Override
    public String process(String text) { return text.toUpperCase(); }
}

class ReplaceProcessor implements TextProcessor {
    private final String target, replacement;

    ReplaceProcessor(String target, String replacement) {
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) { return text.replace(target, replacement); }
}

// ============================================================
// MAIN
// ============================================================

public class CompleteJava2 {

    public static void main(String[] args) throws Exception {
        System.out.println("=== var ===");
        VarDemo.varExamples();

        System.out.println("\n=== Regex ===");
        RegexDemo.basicMatching();
        RegexDemo.namedGroups();

        System.out.println("\n=== Immutable Collections ===");
        ImmutableCollectionsDemo.listOf();
        ImmutableCollectionsDemo.setOf();
        ImmutableCollectionsDemo.mapOf();

        System.out.println("\n=== Special Collections ===");
        SpecialCollectionsDemo.enumMapDemo();
        SpecialCollectionsDemo.enumSetDemo();
        SpecialCollectionsDemo.bitSetDemo();

        System.out.println("\n=== StringJoiner ===");
        StringUtilsDemo.stringJoinerDemo();

        System.out.println("\n=== Enum with Abstract Method ===");
        for (Operation op : Operation.values()) {
            System.out.printf("10 %s 3 = %.1f%n", op, op.apply(10, 3));
        }

        System.out.println("\n=== Private Interface Methods ===");
        Logger logger = Logger.console();
        logger.logInfo("Application started");
        logger.logWarning("Deprecated API used");

        System.out.println("\n=== Covariant Return ===");
        Dog dog = new Dog().setName("Rex").setBreed("Labrador");
        Dog cloned = dog.clone();
        System.out.println(cloned);

        System.out.println("\n=== Result<T> Sealed ===");
        Result<Integer> success = Result.of(() -> 42);
        Result<Integer> failure = Result.of(() -> { throw new RuntimeException("oops"); });
        System.out.println(success.getOrElse(0));
        System.out.println(failure.getOrElse(-1));
        System.out.println(success.map(n -> n * 2).getOrElse(0));

        System.out.println("\n=== Range Record ===");
        Range r = new Range(1, 10);
        System.out.println(r.contains(5));
        System.out.println(r.overlaps(new Range(8, 15)));
        System.out.println(r.intersection(new Range(5, 15)));
        r.stream().forEach(n -> System.out.print(n + " "));

        System.out.println("\n\n=== QueryBuilder ===");
        String query = QueryBuilder.from("users")
            .select("id", "name", "email")
            .where("active = true")
            .where("age > 18")
            .orderBy("name")
            .limit(10)
            .offset(20)
            .build();
        System.out.println(query);

        System.out.println("\n=== TextProcessor Chain ===");
        TextProcessor pipeline = new TrimProcessor()
            .andThen(new UpperCaseProcessor())
            .andThen(new ReplaceProcessor("HELLO", "HI"));
        System.out.println(pipeline.process("  hello world  "));

        System.out.println("\n=== EventBus ===");
        EventBus<String> bus = new EventBus<>();
        bus.subscribe("login", event -> System.out.println("User logged in: " + event));
        bus.subscribe("login", event -> System.out.println("Audit: " + event));
        bus.publish("login", "alice");

        System.out.println("\n=== Flow API ===");
        FlowDemo.demo();

        System.out.println("\n=== ForkJoin ===");
        ForkJoinDemo.demo();

        System.out.println("\n=== System ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
    }
}