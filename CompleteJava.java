import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.math.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

// ============================================================
// ANNOTATIONS
// ============================================================

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@interface CustomAnnotation {
    String value() default "default";
    int priority() default 0;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@interface NotNull {}

@Repeatable(RepeatableContainer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RepeatableAnnotation {
    String tag();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RepeatableContainer {
    RepeatableAnnotation[] value();
}

// ============================================================
// ENUMS
// ============================================================

enum Season {
    SPRING, SUMMER, AUTUMN, WINTER;

    public boolean isWarm() {
        return this == SPRING || this == SUMMER;
    }
}

enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS(4.869e+24, 6.0518e6),
    EARTH(5.976e+24, 6.37814e6),
    MARS(6.421e+23, 3.3972e6);

    private final double mass;
    private final double radius;
    static final double G = 6.67300E-11;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    double surfaceGravity() {
        return G * mass / (radius * radius);
    }

    double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }
}

// ============================================================
// INTERFACES
// ============================================================

interface Drawable {
    void draw();
    default String getDescription() {
        return "Drawable object";
    }
    static Drawable createEmpty() {
        return () -> System.out.println("Empty draw");
    }
}

interface Resizable {
    void resize(double factor);
}

interface Shape extends Drawable, Resizable {
    double area();
    double perimeter();

    default void printInfo() {
        System.out.println("Area: " + area() + ", Perimeter: " + perimeter());
    }
}

@FunctionalInterface
interface MathOperation {
    double operate(double a, double b);
}

@FunctionalInterface
interface StringTransformer {
    String transform(String input);
}

@FunctionalInterface
interface TriFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}

// ============================================================
// ABSTRACT CLASS
// ============================================================

abstract class AbstractShape implements Shape {
    protected String color;
    protected double x, y;

    public AbstractShape(String color, double x, double y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public double getX() { return x; }
    public double getY() { return y; }

    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        System.out.println("Drawing " + getClass().getSimpleName() + " at (" + x + ", " + y + ")");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[color=" + color + ", x=" + x + ", y=" + y + "]";
    }
}

// ============================================================
// CONCRETE CLASSES + INHERITANCE
// ============================================================

class Circle extends AbstractShape {
    private double radius;

    public Circle(String color, double x, double y, double radius) {
        super(color, x, y);
        this.radius = radius;
    }

    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void resize(double factor) {
        radius *= factor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0 &&
               Objects.equals(color, circle.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, radius);
    }
}

class Rectangle extends AbstractShape {
    protected double width, height;

    public Rectangle(String color, double x, double y, double width, double height) {
        super(color, x, y);
        this.width = width;
        this.height = height;
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public void resize(double factor) {
        width *= factor;
        height *= factor;
    }
}

class Square extends Rectangle {
    public Square(String color, double x, double y, double side) {
        super(color, x, y, side, side);
    }

    public double getSide() { return width; }

    public void setSide(double side) {
        this.width = side;
        this.height = side;
    }

    @Override
    public void resize(double factor) {
        setSide(width * factor);
    }
}

// ============================================================
// GENERICS
// ============================================================

class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() { return first; }
    public B getSecond() { return second; }

    public Pair<B, A> swap() {
        return new Pair<>(second, first);
    }

    public <C> Pair<A, C> mapSecond(Function<B, C> f) {
        return new Pair<>(first, f.apply(second));
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

class Triple<A, B, C> extends Pair<A, B> {
    private final C third;

    public Triple(A first, B second, C third) {
        super(first, second);
        this.third = third;
    }

    public C getThird() { return third; }
}

class BoundedBox<T extends Number & Comparable<T>> {
    private T value;

    public BoundedBox(T value) { this.value = value; }
    public T getValue() { return value; }

    public double doubled() {
        return value.doubleValue() * 2;
    }
}

class GenericStack<T> {
    private final LinkedList<T> list = new LinkedList<>();

    public void push(T item) { list.addFirst(item); }
    public T pop() { return list.removeFirst(); }
    public T peek() { return list.getFirst(); }
    public boolean isEmpty() { return list.isEmpty(); }
    public int size() { return list.size(); }

    @SafeVarargs
    public static <T> GenericStack<T> of(T... items) {
        GenericStack<T> stack = new GenericStack<>();
        for (T item : items) stack.push(item);
        return stack;
    }
}

class WildcardDemo {
    public static double sumList(List<? extends Number> list) {
        return list.stream().mapToDouble(Number::doubleValue).sum();
    }

    public static void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (T item : src) dest.add(item);
    }
}

// ============================================================
// EXCEPTION HANDLING
// ============================================================

class AppException extends RuntimeException {
    private final int errorCode;

    public AppException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() { return errorCode; }
}

class ValidationException extends AppException {
    private final String fieldName;

    public ValidationException(String fieldName, String message) {
        super(message, 400);
        this.fieldName = fieldName;
    }

    public String getFieldName() { return fieldName; }
}

class ExceptionDemo {
    public static int divide(int a, int b) {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }

    public static String readFileSafe(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new AppException("Failed to read file: " + path, 500, e);
        }
    }

    public static void multiCatch(String input) {
        try {
            int value = Integer.parseInt(input);
            int result = 100 / value;
            System.out.println(result);
        } catch (NumberFormatException | ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed");
        }
    }

    public static void tryWithResources(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void chainedExceptions() {
        try {
            try {
                throw new RuntimeException("Original cause");
            } catch (RuntimeException e) {
                throw new AppException("Wrapped exception", 500, e);
            }
        } catch (AppException e) {
            System.out.println(e.getMessage());
            System.out.println("Caused by: " + e.getCause().getMessage());
        }
    }
}

// ============================================================
// COLLECTIONS
// ============================================================

class CollectionsDemo {

    public static void arrayListDemo() {
        List<String> list = new ArrayList<>();
        list.add("Alpha");
        list.add("Beta");
        list.add("Gamma");
        list.add(1, "Delta");
        list.remove("Beta");
        list.remove(0);
        list.set(0, "Epsilon");
        list.contains("Gamma");
        list.indexOf("Gamma");
        list.subList(0, 2);
        Collections.sort(list);
        Collections.reverse(list);
        Collections.shuffle(list);
        Collections.swap(list, 0, 1);
        Collections.fill(list, "X");
        Collections.frequency(list, "X");
    }

    public static void linkedListDemo() {
        LinkedList<Integer> ll = new LinkedList<>();
        ll.addFirst(1);
        ll.addLast(2);
        ll.offerFirst(0);
        ll.offerLast(3);
        ll.peekFirst();
        ll.peekLast();
        ll.pollFirst();
        ll.pollLast();
        ll.push(99);
        ll.pop();
        ll.descendingIterator();
    }

    public static void hashMapDemo() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.putIfAbsent("c", 3);
        map.get("a");
        map.getOrDefault("z", 0);
        map.containsKey("b");
        map.containsValue(2);
        map.remove("b");
        map.replace("a", 99);
        map.replace("a", 99, 100);
        map.merge("a", 1, Integer::sum);
        map.compute("a", (k, v) -> v == null ? 1 : v + 1);
        map.computeIfAbsent("d", k -> k.length());
        map.computeIfPresent("a", (k, v) -> v * 2);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            entry.getKey();
            entry.getValue();
        }
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    public static void treeMapDemo() {
        TreeMap<String, Integer> tm = new TreeMap<>();
        tm.put("banana", 2);
        tm.put("apple", 1);
        tm.put("cherry", 3);
        tm.firstKey();
        tm.lastKey();
        tm.headMap("cherry");
        tm.tailMap("banana");
        tm.subMap("apple", "cherry");
        tm.floorKey("blueberry");
        tm.ceilingKey("blueberry");
        tm.lowerKey("cherry");
        tm.higherKey("banana");
    }

    public static void linkedHashMapDemo() {
        LinkedHashMap<String, Integer> lhm = new LinkedHashMap<>(16, 0.75f, true);
        lhm.put("one", 1);
        lhm.put("two", 2);
        lhm.put("three", 3);
        lhm.get("one");
    }

    public static void hashSetDemo() {
        Set<String> set = new HashSet<>();
        set.add("x");
        set.add("y");
        set.add("x");
        set.remove("y");
        set.contains("x");
        set.size();
        Set<String> other = new HashSet<>(Arrays.asList("x", "z"));
        set.retainAll(other);
        set.addAll(other);
        set.removeAll(other);
    }

    public static void treeSetDemo() {
        TreeSet<Integer> ts = new TreeSet<>();
        ts.add(5);
        ts.add(1);
        ts.add(3);
        ts.add(7);
        ts.first();
        ts.last();
        ts.headSet(5);
        ts.tailSet(3);
        ts.subSet(1, 5);
        ts.floor(4);
        ts.ceiling(4);
        ts.lower(5);
        ts.higher(5);
        ts.descendingSet();
        ts.pollFirst();
        ts.pollLast();
    }

    public static void priorityQueueDemo() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(5);
        pq.offer(1);
        pq.offer(3);
        pq.peek();
        pq.poll();
        pq.size();

        PriorityQueue<String> maxPQ = new PriorityQueue<>(Comparator.reverseOrder());
        maxPQ.offer("banana");
        maxPQ.offer("apple");
        maxPQ.offer("cherry");
    }

    public static void dequeDemo() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.offerFirst(1);
        deque.offerLast(2);
        deque.peekFirst();
        deque.peekLast();
        deque.pollFirst();
        deque.pollLast();
    }

    public static void stackDemo() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.peek();
        stack.pop();
        stack.empty();
        stack.search("a");
    }

    public static void collectionsUtilsDemo() {
        List<Integer> nums = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));
        Collections.sort(nums);
        Collections.sort(nums, Comparator.reverseOrder());
        Collections.binarySearch(nums, 5);
        Collections.min(nums);
        Collections.max(nums);
        Collections.nCopies(5, "hello");
        Collections.singletonList("only");
        Collections.emptyList();
        Collections.unmodifiableList(nums);
        Collections.synchronizedList(nums);
        Collections.disjoint(nums, Arrays.asList(10, 11));
    }
}

// ============================================================
// STREAMS
// ============================================================

class StreamsDemo {

    public static void creatingStreams() {
        Stream<String> fromList = Arrays.asList("a", "b", "c").stream();
        Stream<String> fromOf = Stream.of("x", "y", "z");
        Stream<Integer> fromIterate = Stream.iterate(0, n -> n + 2).limit(10);
        Stream<Double> fromGenerate = Stream.generate(Math::random).limit(5);
        IntStream fromRange = IntStream.range(0, 10);
        IntStream fromRangeClosed = IntStream.rangeClosed(1, 10);
        LongStream longStream = LongStream.range(0L, 100L);
        DoubleStream doubleStream = DoubleStream.of(1.1, 2.2, 3.3);
        Stream<String> fromArray = Arrays.stream(new String[]{"a", "b"});
        Stream<String> empty = Stream.empty();
        Stream<String> concat = Stream.concat(fromOf, Stream.of("w"));
    }

    public static void intermediateOperations() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbers.stream()
               .filter(n -> n % 2 == 0)
               .map(n -> n * n)
               .sorted()
               .distinct()
               .limit(3)
               .skip(1)
               .peek(n -> System.out.print(n + " "))
               .forEach(System.out::println);

        List<List<Integer>> nested = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6)
        );
        nested.stream()
              .flatMap(Collection::stream)
              .collect(Collectors.toList());

        numbers.stream()
               .mapToInt(Integer::intValue)
               .average()
               .ifPresent(System.out::println);

        numbers.stream()
               .sorted(Comparator.reverseOrder())
               .collect(Collectors.toList());

        numbers.stream()
               .takeWhile(n -> n < 5)
               .collect(Collectors.toList());

        numbers.stream()
               .dropWhile(n -> n < 5)
               .collect(Collectors.toList());
    }

    public static void terminalOperations() {
        List<String> words = Arrays.asList("hello", "world", "java", "stream");

        words.stream().forEach(System.out::println);

        long count = words.stream().filter(w -> w.length() > 4).count();

        Optional<String> first = words.stream().filter(w -> w.startsWith("j")).findFirst();
        Optional<String> any = words.stream().filter(w -> w.length() > 3).findAny();

        boolean anyMatch = words.stream().anyMatch(w -> w.contains("a"));
        boolean allMatch = words.stream().allMatch(w -> w.length() > 2);
        boolean noneMatch = words.stream().noneMatch(w -> w.isEmpty());

        Optional<String> min = words.stream().min(Comparator.naturalOrder());
        Optional<String> max = words.stream().max(Comparator.naturalOrder());

        String joined = words.stream().reduce("", (a, b) -> a + " " + b);
        Optional<String> reduced = words.stream().reduce((a, b) -> a.length() > b.length() ? a : b);

        List<String> toList = words.stream().collect(Collectors.toList());
        Set<String> toSet = words.stream().collect(Collectors.toSet());
        String joiningResult = words.stream().collect(Collectors.joining(", ", "[", "]"));

        Map<Integer, List<String>> grouped = words.stream()
            .collect(Collectors.groupingBy(String::length));

        Map<Boolean, List<String>> partitioned = words.stream()
            .collect(Collectors.partitioningBy(w -> w.length() > 4));

        Map<Integer, Long> countByLength = words.stream()
            .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        IntSummaryStatistics stats = words.stream()
            .mapToInt(String::length)
            .summaryStatistics();
        stats.getMin();
        stats.getMax();
        stats.getAverage();
        stats.getSum();
        stats.getCount();

        int[] array = words.stream().mapToInt(String::length).toArray();
    }

    public static void parallelStreams() {
        List<Integer> list = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList());

        long sum = list.parallelStream()
                       .filter(n -> n % 2 == 0)
                       .mapToLong(Integer::longValue)
                       .sum();

        Map<Integer, List<Integer>> grouped = list.parallelStream()
            .collect(Collectors.groupingByConcurrent(n -> n % 10));
    }

    public static void optionalDemo() {
        Optional<String> opt = Optional.of("hello");
        Optional<String> empty = Optional.empty();
        Optional<String> nullable = Optional.ofNullable(null);

        opt.isPresent();
        opt.isEmpty();
        opt.get();
        opt.orElse("default");
        opt.orElseGet(() -> "computed");
        opt.orElseThrow(() -> new RuntimeException("No value"));
        opt.ifPresent(System.out::println);
        opt.ifPresentOrElse(System.out::println, () -> System.out.println("empty"));
        opt.map(String::toUpperCase);
        opt.flatMap(s -> Optional.of(s.toUpperCase()));
        opt.filter(s -> s.length() > 3);
        opt.or(() -> Optional.of("alternative"));
        opt.stream().collect(Collectors.toList());
    }
}

// ============================================================
// LAMBDAS & FUNCTIONAL INTERFACES
// ============================================================

class LambdaDemo {

    public static void builtInFunctionalInterfaces() {
        Function<String, Integer> strLen = String::length;
        Function<String, String> upper = String::toUpperCase;
        Function<String, String> composed = upper.andThen(s -> s + "!");
        Function<String, String> composed2 = upper.compose(s -> s.trim());

        BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);

        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isPositive = n -> n > 0;
        Predicate<Integer> both = isEven.and(isPositive);
        Predicate<Integer> either = isEven.or(isPositive);
        Predicate<Integer> notEven = isEven.negate();
        Predicate<Integer> always = Predicate.not(isEven);

        Consumer<String> printer = System.out::println;
        Consumer<String> printerWithNewLine = printer.andThen(s -> System.out.println("---"));

        BiConsumer<String, Integer> biConsumer = (s, n) -> System.out.println(s + n);

        Supplier<List<String>> listFactory = ArrayList::new;
        Supplier<LocalDate> today = LocalDate::now;

        UnaryOperator<String> trim = String::trim;
        BinaryOperator<Integer> add = Integer::sum;
        BinaryOperator<Integer> max = BinaryOperator.maxBy(Comparator.naturalOrder());

        Comparator<String> byLength = Comparator.comparingInt(String::length);
        Comparator<String> byLengthThenAlpha = byLength.thenComparing(Comparator.naturalOrder());
        Comparator<String> reversed = byLength.reversed();
        Comparator<String> nullSafe = Comparator.nullsFirst(byLength);
    }

    public static void methodReferences() {
        List<String> words = Arrays.asList("hello", "world", "java");

        words.forEach(System.out::println);
        words.stream().map(String::toUpperCase).collect(Collectors.toList());
        words.stream().filter(String::isEmpty).collect(Collectors.toList());
        words.sort(String::compareTo);

        Function<String, Integer> len = String::length;
        Supplier<List<String>> newList = ArrayList::new;
        Function<String, String[]> split = s -> s.split(",");

        List<Integer> nums = Arrays.asList(1, 2, 3);
        nums.stream().map(String::valueOf).collect(Collectors.toList());
    }

    public static void closuresAndCapture() {
        String prefix = "Hello, ";
        Function<String, String> greeter = name -> prefix + name;
        System.out.println(greeter.apply("World"));

        int[] counter = {0};
        Runnable increment = () -> counter[0]++;
        increment.run();
        System.out.println(counter[0]);
    }

    public static void customFunctionalInterfaces() {
        MathOperation add = (a, b) -> a + b;
        MathOperation multiply = (a, b) -> a * b;
        MathOperation power = Math::pow;

        System.out.println(add.operate(5, 3));
        System.out.println(multiply.operate(4, 7));

        StringTransformer reverse = s -> new StringBuilder(s).reverse().toString();
        StringTransformer shout = s -> s.toUpperCase() + "!!!";

        TriFunction<Integer, Integer, Integer, Integer> clamp =
            (value, min, max) -> Math.max(min, Math.min(max, value));

        System.out.println(clamp.apply(15, 0, 10));
    }
}

// ============================================================
// CONCURRENCY
// ============================================================

class ConcurrencyDemo {

    private int unsafeCounter = 0;
    private final AtomicInteger atomicCounter = new AtomicInteger(0);
    private final ReentrantLock lock = new ReentrantLock();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private volatile boolean running = true;

    public void basicThreadCreation() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        });
        t1.setName("Worker-1");
        t1.setDaemon(false);
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t1.join();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Runnable");
            }
        });
        t2.start();
        t2.join(1000);
    }

    public synchronized void synchronizedMethod() {
        unsafeCounter++;
    }

    public void synchronizedBlock() {
        synchronized (this) {
            unsafeCounter++;
        }
    }

    public void lockDemo() {
        lock.lock();
        try {
            unsafeCounter++;
        } finally {
            lock.unlock();
        }
    }

    public void tryLockDemo() {
        if (lock.tryLock()) {
            try {
                unsafeCounter++;
            } finally {
                lock.unlock();
            }
        }
    }

    public void readWriteLockDemo() {
        rwLock.readLock().lock();
        try {
            System.out.println(unsafeCounter);
        } finally {
            rwLock.readLock().unlock();
        }

        rwLock.writeLock().lock();
        try {
            unsafeCounter++;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void atomicDemo() {
        atomicCounter.incrementAndGet();
        atomicCounter.decrementAndGet();
        atomicCounter.addAndGet(5);
        atomicCounter.getAndSet(0);
        atomicCounter.compareAndSet(0, 100);

        AtomicLong al = new AtomicLong(0L);
        AtomicBoolean ab = new AtomicBoolean(false);
        AtomicReference<String> ar = new AtomicReference<>("initial");
        ar.compareAndSet("initial", "updated");
    }

    public void executorServiceDemo() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ExecutorService single = Executors.newSingleThreadExecutor();
        ExecutorService cached = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);

        executor.submit(() -> System.out.println("Task 1"));
        Future<Integer> future = executor.submit(() -> 42);

        try {
            Integer result = future.get(1, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (TimeoutException e) {
            future.cancel(true);
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdownNow();

        scheduled.schedule(() -> System.out.println("Delayed"), 1, TimeUnit.SECONDS);
        scheduled.scheduleAtFixedRate(() -> System.out.println("Periodic"), 0, 1, TimeUnit.SECONDS);
        scheduled.scheduleWithFixedDelay(() -> System.out.println("Fixed Delay"), 0, 1, TimeUnit.SECONDS);
        scheduled.shutdown();
    }

    public void completableFutureDemo() throws Exception {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "Hello")
            .thenApply(s -> s + " World")
            .thenApply(String::toUpperCase);

        CompletableFuture<Void> print = cf.thenAccept(System.out::println);

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "First");
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "Second");

        CompletableFuture<String> both = f1.thenCombine(f2, (a, b) -> a + " " + b);
        CompletableFuture<Object> either = CompletableFuture.anyOf(f1, f2);
        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);

        CompletableFuture<String> withError = CompletableFuture
            .supplyAsync(() -> { throw new RuntimeException("Error"); })
            .exceptionally(e -> "Recovered: " + e.getMessage());

        CompletableFuture<String> handled = CompletableFuture
            .supplyAsync(() -> "value")
            .handle((result, ex) -> ex != null ? "Error" : result.toUpperCase());

        cf.get();
        cf.getNow("default");
        cf.join();
    }

    public void concurrentCollections() {
        ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>();
        chm.put("a", 1);
        chm.putIfAbsent("b", 2);
        chm.computeIfAbsent("c", k -> k.length());
        chm.merge("a", 1, Integer::sum);
        chm.forEach(1, (k, v) -> System.out.println(k + "=" + v));
        chm.reduce(1, (k, v) -> v, Integer::sum);

        CopyOnWriteArrayList<String> cowal = new CopyOnWriteArrayList<>();
        cowal.add("x");
        cowal.addIfAbsent("y");

        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>(10);
        try {
            bq.put(1);
            bq.offer(2, 1, TimeUnit.SECONDS);
            bq.take();
            bq.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ConcurrentLinkedQueue<String> clq = new ConcurrentLinkedQueue<>();
        clq.offer("item");
        clq.poll();
        clq.peek();
    }

    public void semaphoreDemo() throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);
        semaphore.acquire();
        try {
            System.out.println("In critical section");
        } finally {
            semaphore.release();
        }
        semaphore.tryAcquire();
        semaphore.availablePermits();
    }

    public void countDownLatchDemo() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println("Working...");
                latch.countDown();
            }).start();
        }

        latch.await();
        latch.await(5, TimeUnit.SECONDS);
        System.out.println("All done");
    }

    public void cyclicBarrierDemo() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Barrier reached!"));

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Waiting at barrier");
                    barrier.await();
                    System.out.println("Passed barrier");
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}

// ============================================================
// DATE & TIME (java.time)
// ============================================================

class DateTimeDemo {

    public static void localDateDemo() {
        LocalDate today = LocalDate.now();
        LocalDate specific = LocalDate.of(2024, Month.JANUARY, 15);
        LocalDate parsed = LocalDate.parse("2024-06-15");
        LocalDate parsed2 = LocalDate.parse("15/06/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        today.getYear();
        today.getMonth();
        today.getMonthValue();
        today.getDayOfMonth();
        today.getDayOfWeek();
        today.getDayOfYear();
        today.isLeapYear();
        today.lengthOfMonth();
        today.lengthOfYear();

        today.plusDays(7);
        today.plusWeeks(2);
        today.plusMonths(1);
        today.plusYears(1);
        today.minusDays(3);
        today.withDayOfMonth(1);
        today.withMonth(6);

        today.isBefore(specific);
        today.isAfter(specific);
        today.isEqual(specific);
        today.compareTo(specific);

        today.atStartOfDay();
        today.atTime(12, 0);
        today.atTime(LocalTime.NOON);
    }

    public static void localTimeDemo() {
        LocalTime now = LocalTime.now();
        LocalTime noon = LocalTime.NOON;
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime specific = LocalTime.of(14, 30, 0);
        LocalTime parsed = LocalTime.parse("14:30:00");

        now.getHour();
        now.getMinute();
        now.getSecond();
        now.getNano();

        now.plusHours(2);
        now.plusMinutes(30);
        now.plusSeconds(45);
        now.minusHours(1);
        now.withHour(10);

        now.isBefore(noon);
        now.isAfter(midnight);
    }

    public static void localDateTimeDemo() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime specific = LocalDateTime.of(2024, 6, 15, 14, 30, 0);

        now.toLocalDate();
        now.toLocalTime();
        now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public static void zonedDateTimeDemo() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime sp = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);

        now.getZone();
        now.toLocalDateTime();
        now.toInstant();
        now.withZoneSameInstant(ZoneId.of("Europe/London"));
        now.withZoneSameLocal(ZoneId.of("Asia/Tokyo"));

        ZoneId.getAvailableZoneIds();
        ZoneOffset.of("+03:00");
    }

    public static void instantDemo() {
        Instant now = Instant.now();
        Instant epoch = Instant.EPOCH;
        Instant fromEpoch = Instant.ofEpochSecond(1000000L);
        Instant fromMilli = Instant.ofEpochMilli(System.currentTimeMillis());

        now.getEpochSecond();
        now.getNano();
        now.toEpochMilli();
        now.plusSeconds(60);
        now.minusMillis(500);
        now.isBefore(epoch);
        now.isAfter(epoch);
    }

    public static void durationPeriodDemo() {
        Duration duration = Duration.ofHours(2).plusMinutes(30);
        Duration d2 = Duration.between(LocalTime.NOON, LocalTime.now());

        duration.toHours();
        duration.toMinutes();
        duration.toSeconds();
        duration.toMillis();
        duration.toNanos();
        duration.negated();
        duration.abs();
        duration.multipliedBy(2);
        duration.dividedBy(3);

        Period period = Period.of(1, 2, 3);
        Period p2 = Period.between(LocalDate.of(2020, 1, 1), LocalDate.now());

        period.getYears();
        period.getMonths();
        period.getDays();
        period.toTotalMonths();
        period.isNegative();
        period.isZero();
    }

    public static void temporalAdjustersDemo() {
        LocalDate date = LocalDate.now();

        date.with(TemporalAdjusters.firstDayOfMonth());
        date.with(TemporalAdjusters.lastDayOfMonth());
        date.with(TemporalAdjusters.firstDayOfYear());
        date.with(TemporalAdjusters.lastDayOfYear());
        date.with(TemporalAdjusters.firstDayOfNextMonth());
        date.with(TemporalAdjusters.firstDayOfNextYear());
        date.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        date.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));
        date.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.TUESDAY));
    }
}

// ============================================================
// STRING OPERATIONS
// ============================================================

class StringDemo {

    public static void basicOperations() {
        String s = "Hello, World!";

        s.length();
        s.charAt(0);
        s.indexOf('o');
        s.indexOf("World");
        s.lastIndexOf('l');
        s.substring(7);
        s.substring(7, 12);
        s.toUpperCase();
        s.toLowerCase();
        s.trim();
        s.strip();
        s.stripLeading();
        s.stripTrailing();
        s.replace('l', 'r');
        s.replace("World", "Java");
        s.replaceAll("[aeiou]", "*");
        s.replaceFirst("[A-Z]", "X");
        s.contains("World");
        s.startsWith("Hello");
        s.endsWith("!");
        s.isEmpty();
        s.isBlank();
        s.equals("Hello, World!");
        s.equalsIgnoreCase("hello, world!");
        s.compareTo("Hello");
        s.compareToIgnoreCase("hello");
        s.split(", ");
        s.split(", ", 2);
        s.toCharArray();
        s.chars();
        s.codePoints();
        s.intern();
        s.repeat(3);
        s.indent(4);
        s.formatted();
        s.lines().collect(Collectors.toList());
        String.valueOf(42);
        String.format("Name: %s, Age: %d", "Alice", 30);
        String.join(", ", "a", "b", "c");
        String.join("-", Arrays.asList("x", "y", "z"));
    }

    public static void stringBuilderDemo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(", ");
        sb.append("World");
        sb.insert(5, " Beautiful");
        sb.delete(5, 15);
        sb.deleteCharAt(0);
        sb.replace(0, 5, "Hi");
        sb.reverse();
        sb.setCharAt(0, 'X');
        sb.charAt(0);
        sb.indexOf("Hi");
        sb.substring(0, 2);
        sb.length();
        sb.capacity();
        sb.ensureCapacity(100);
        sb.trimToSize();
        sb.setLength(0);
        sb.toString();

        StringBuffer sbf = new StringBuffer("thread-safe");
        sbf.append(" version");
    }

    public static void charOperations() {
        char c = 'A';
        Character.isLetter(c);
        Character.isDigit(c);
        Character.isWhitespace(c);
        Character.isUpperCase(c);
        Character.isLowerCase(c);
        Character.toUpperCase(c);
        Character.toLowerCase(c);
        Character.isAlphabetic(c);
        Character.getNumericValue(c);
        Character.compare(c, 'B');
    }
}

// ============================================================
// MATH & NUMBERS
// ============================================================

class MathDemo {

    public static void basicMath() {
        Math.abs(-5);
        Math.max(3, 7);
        Math.min(3, 7);
        Math.pow(2, 10);
        Math.sqrt(144);
        Math.cbrt(27);
        Math.floor(3.7);
        Math.ceil(3.2);
        Math.round(3.5);
        Math.rint(3.5);
        Math.log(Math.E);
        Math.log10(1000);
        Math.exp(1);
        Math.sin(Math.PI / 2);
        Math.cos(0);
        Math.tan(Math.PI / 4);
        Math.asin(1);
        Math.acos(1);
        Math.atan(1);
        Math.atan2(1, 1);
        Math.sinh(1);
        Math.cosh(1);
        Math.tanh(1);
        Math.toDegrees(Math.PI);
        Math.toRadians(180);
        Math.signum(-5.0);
        Math.copySign(3.0, -1.0);
        Math.hypot(3, 4);
        Math.random();
        Math.PI;
        Math.E;
        Math.floorDiv(7, 2);
        Math.floorMod(7, 2);
        Math.addExact(Integer.MAX_VALUE - 1, 1);
        Math.multiplyExact(1000, 1000);
        Math.toIntExact(100L);
    }

    public static void bigDecimalDemo() {
        BigDecimal a = new BigDecimal("10.50");
        BigDecimal b = BigDecimal.valueOf(3.25);
        BigDecimal c = new BigDecimal(42);

        a.add(b);
        a.subtract(b);
        a.multiply(b);
        a.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
        a.remainder(b);
        a.pow(2);
        a.negate();
        a.abs();
        a.scale();
        a.precision();
        a.unscaledValue();
        a.stripTrailingZeros();
        a.setScale(4, RoundingMode.CEILING);
        a.compareTo(b);
        a.max(b);
        a.min(b);
        a.intValue();
        a.doubleValue();
        a.toBigInteger();
        a.toPlainString();
        a.toEngineeringString();
    }

    public static void bigIntegerDemo() {
        BigInteger a = new BigInteger("1000000000000000000");
        BigInteger b = BigInteger.valueOf(999999999L);
        BigInteger c = BigInteger.TWO;

        a.add(b);
        a.subtract(b);
        a.multiply(b);
        a.divide(b);
        a.remainder(b);
        a.mod(b);
        a.pow(3);
        a.negate();
        a.abs();
        a.gcd(b);
        a.and(b);
        a.or(b);
        a.xor(b);
        a.not();
        a.shiftLeft(2);
        a.shiftRight(2);
        a.testBit(0);
        a.setBit(5);
        a.clearBit(5);
        a.flipBit(3);
        a.bitLength();
        a.bitCount();
        a.isProbablePrime(50);
        a.nextProbablePrime();
        BigInteger.probablePrime(128, new Random());
        a.compareTo(b);
        a.intValue();
        a.longValue();
        a.toString(16);
    }

    public static void randomDemo() {
        Random random = new Random();
        Random seeded = new Random(42L);

        random.nextInt();
        random.nextInt(100);
        random.nextLong();
        random.nextDouble();
        random.nextFloat();
        random.nextBoolean();
        random.nextGaussian();
        random.nextBytes(new byte[10]);
        random.ints(10, 0, 100).toArray();
        random.doubles(5, 0.0, 1.0).toArray();
        random.longs(5).toArray();

        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        tlr.nextInt(1, 101);
        tlr.nextDouble(0.0, 1.0);

        SplittableRandom sr = new SplittableRandom(42);
        sr.nextInt(1, 10);
    }
}

// ============================================================
// ARRAYS
// ============================================================

class ArraysDemo {

    public static void basicArrays() {
        int[] arr = new int[5];
        int[] arr2 = {1, 2, 3, 4, 5};
        int[][] matrix = new int[3][3];
        int[][] matrix2 = {{1, 2}, {3, 4}, {5, 6}};
        String[] strs = new String[3];
        int[][][] cube = new int[2][2][2];

        arr.length;

        Arrays.sort(arr2);
        Arrays.sort(arr2, 1, 4);
        Arrays.binarySearch(arr2, 3);
        Arrays.fill(arr, 0);
        Arrays.fill(arr, 1, 4, 99);
        Arrays.copyOf(arr2, 10);
        Arrays.copyOfRange(arr2, 1, 4);
        Arrays.equals(arr, arr2);
        Arrays.deepEquals(matrix, matrix2);
        Arrays.toString(arr2);
        Arrays.deepToString(matrix2);
        Arrays.stream(arr2);
        Arrays.stream(arr2, 0, 3);
        Arrays.asList("a", "b", "c");
        Arrays.parallelSort(arr2);
        Arrays.parallelPrefix(arr2, Integer::sum);
        Arrays.spliterator(arr2);
        System.arraycopy(arr2, 0, arr, 0, 3);
    }
}

// ============================================================
// I/O & NIO
// ============================================================

class IODemo {

    public static void fileOperations() throws IOException {
        Path path = Path.of("/tmp/example.txt");
        Path path2 = Paths.get("/tmp", "sub", "file.txt");

        Files.createFile(path);
        Files.createDirectory(Path.of("/tmp/newdir"));
        Files.createDirectories(Path.of("/tmp/a/b/c"));
        Files.createTempFile("prefix", ".tmp");
        Files.createTempDirectory("tempdir");

        Files.write(path, "Hello\nWorld".getBytes());
        Files.write(path, Arrays.asList("line1", "line2"), StandardCharsets.UTF_8);
        Files.writeString(path, "content");

        Files.readAllBytes(path);
        Files.readAllLines(path);
        Files.readAllLines(path, StandardCharsets.UTF_8);
        Files.readString(path);

        Files.copy(path, Path.of("/tmp/copy.txt"), StandardCopyOption.REPLACE_EXISTING);
        Files.move(path, Path.of("/tmp/moved.txt"), StandardCopyOption.REPLACE_EXISTING);
        Files.delete(path);
        Files.deleteIfExists(path);

        Files.exists(path);
        Files.notExists(path);
        Files.isReadable(path);
        Files.isWritable(path);
        Files.isDirectory(path);
        Files.isRegularFile(path);
        Files.isHidden(path);
        Files.size(path);
        Files.getLastModifiedTime(path);
        Files.probeContentType(path);

        try (Stream<Path> list = Files.list(Path.of("/tmp"))) {
            list.forEach(System.out::println);
        }

        try (Stream<Path> walk = Files.walk(Path.of("/tmp"))) {
            walk.filter(Files::isRegularFile).forEach(System.out::println);
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.lines().forEach(System.out::println);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("content");
            writer.newLine();
        }
    }

    public static void streamIODemo() throws IOException {
        FileInputStream fis = new FileInputStream("/tmp/file.bin");
        fis.read();
        fis.read(new byte[10]);
        fis.available();
        fis.skip(5);
        fis.close();

        FileOutputStream fos = new FileOutputStream("/tmp/out.bin");
        fos.write(65);
        fos.write(new byte[]{1, 2, 3});
        fos.flush();
        fos.close();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("/tmp/file.bin"));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("/tmp/out.bin"))) {
            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b);
            }
        }

        ByteArrayInputStream baos_in = new ByteArrayInputStream("hello".getBytes());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(72);
        baos.toByteArray();
        baos.toString();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("/tmp/data.bin"));
             DataInputStream dis = new DataInputStream(new FileInputStream("/tmp/data.bin"))) {
            dos.writeInt(42);
            dos.writeDouble(3.14);
            dos.writeUTF("hello");
            dos.writeBoolean(true);
        } catch (IOException ignored) {}

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/tmp/obj.bin"));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/tmp/obj.bin"))) {
            oos.writeObject("serialized string");
            oos.flush();
        } catch (IOException ignored) {}
    }

    public static void readerWriterDemo() throws IOException {
        StringReader sr = new StringReader("hello world");
        StringWriter sw = new StringWriter();
        sw.write("output");
        sw.toString();

        CharArrayReader car = new CharArrayReader("chars".toCharArray());
        CharArrayWriter caw = new CharArrayWriter();
        caw.write("char output");
        caw.toCharArray();

        try (PrintWriter pw = new PrintWriter(new FileWriter("/tmp/print.txt"))) {
            pw.println("line 1");
            pw.printf("Name: %s, Value: %d%n", "test", 42);
            pw.format("%.2f%n", 3.14159);
        }

        try (LineNumberReader lnr = new LineNumberReader(new FileReader("/tmp/print.txt"))) {
            lnr.readLine();
            lnr.getLineNumber();
        } catch (IOException ignored) {}
    }
}

// ============================================================
// REFLECTION
// ============================================================

class ReflectionDemo {

    @CustomAnnotation(value = "example", priority = 1)
    private String name = "John";
    private int age = 30;

    public ReflectionDemo() {}
    public ReflectionDemo(String name) { this.name = name; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public static void demo() throws Exception {
        Class<?> clazz = ReflectionDemo.class;
        Class<?> fromName = Class.forName("ReflectionDemo");
        Class<?> fromInstance = new ReflectionDemo().getClass();

        clazz.getName();
        clazz.getSimpleName();
        clazz.getPackageName();
        clazz.getSuperclass();
        clazz.getInterfaces();
        clazz.getModifiers();
        Modifier.isPublic(clazz.getModifiers());

        clazz.getDeclaredFields();
        clazz.getFields();
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        ReflectionDemo obj = new ReflectionDemo();
        field.get(obj);
        field.set(obj, "Jane");
        field.getType();
        field.getModifiers();
        field.getAnnotation(CustomAnnotation.class);

        clazz.getDeclaredMethods();
        clazz.getMethods();
        Method method = clazz.getMethod("getName");
        method.invoke(obj);
        method.getReturnType();
        method.getParameterTypes();
        method.getAnnotations();

        clazz.getDeclaredConstructors();
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        ReflectionDemo newObj = (ReflectionDemo) constructor.newInstance("Alice");

        clazz.isAnnotationPresent(CustomAnnotation.class);
        clazz.getAnnotations();
        clazz.getDeclaredAnnotations();

        clazz.isInterface();
        clazz.isEnum();
        clazz.isArray();
        clazz.isPrimitive();
        clazz.isAnonymousClass();
        clazz.isLocalClass();
        clazz.isMemberClass();
        clazz.isAssignableFrom(Square.class);

        Object proxy = java.lang.reflect.Proxy.newProxyInstance(
            clazz.getClassLoader(),
            new Class[]{Drawable.class},
            (proxyObj, m, args) -> {
                System.out.println("Intercepted: " + m.getName());
                return null;
            }
        );
    }
}

// ============================================================
// INNER CLASSES
// ============================================================

class OuterClass {
    private int outerValue = 10;

    class InnerClass {
        private int innerValue = 20;

        int sum() {
            return outerValue + innerValue;
        }
    }

    static class StaticNestedClass {
        static int staticValue = 30;

        int getValue() {
            return staticValue;
        }
    }

    interface NestedInterface {
        void execute();
    }

    enum NestedEnum {
        OPTION_A, OPTION_B, OPTION_C
    }

    void methodWithLocalClass() {
        int localVar = 100;

        class LocalClass {
            void show() {
                System.out.println("Local: " + localVar);
            }
        }

        new LocalClass().show();
    }

    void methodWithAnonymousClass() {
        Drawable d = new Drawable() {
            @Override
            public void draw() {
                System.out.println("Anonymous draw: " + outerValue);
            }
        };
        d.draw();
    }

    Runnable getLambda() {
        return () -> System.out.println("Lambda in outer: " + outerValue);
    }
}

// ============================================================
// SEALED CLASSES (Java 17+)
// ============================================================

sealed interface Expr permits Num, Add, Mul {}
record Num(int value) implements Expr {}
record Add(Expr left, Expr right) implements Expr {}
record Mul(Expr left, Expr right) implements Expr {}

class ExprEvaluator {
    public static int eval(Expr expr) {
        return switch (expr) {
            case Num(int v) -> v;
            case Add(Expr l, Expr r) -> eval(l) + eval(r);
            case Mul(Expr l, Expr r) -> eval(l) * eval(r);
        };
    }
}

// ============================================================
// RECORDS
// ============================================================

record Point(double x, double y) {
    static final Point ORIGIN = new Point(0, 0);

    Point {
        if (Double.isNaN(x) || Double.isNaN(y)) {
            throw new IllegalArgumentException("NaN not allowed");
        }
    }

    double distanceTo(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.hypot(dx, dy);
    }

    Point translate(double dx, double dy) {
        return new Point(x + dx, y + dy);
    }

    @Override
    public String toString() {
        return String.format("Point(%.2f, %.2f)", x, y);
    }
}

record Person(String name, int age) implements Comparable<Person> {
    @Override
    public int compareTo(Person other) {
        return Comparator.comparing(Person::name)
                         .thenComparingInt(Person::age)
                         .compare(this, other);
    }
}

// ============================================================
// PATTERN MATCHING
// ============================================================

class PatternMatchingDemo {

    public static String describeObject(Object obj) {
        return switch (obj) {
            case Integer i when i > 0 -> "Positive integer: " + i;
            case Integer i -> "Non-positive integer: " + i;
            case String s when s.isBlank() -> "Blank string";
            case String s -> "String of length " + s.length();
            case int[] arr -> "int array of length " + arr.length;
            case null -> "null";
            default -> "Unknown: " + obj.getClass().getSimpleName();
        };
    }

    public static void instanceofPattern(Object obj) {
        if (obj instanceof String s && s.length() > 5) {
            System.out.println("Long string: " + s.toUpperCase());
        }

        if (obj instanceof Integer i) {
            System.out.println("Integer doubled: " + (i * 2));
        }
    }

    public static void switchExpressions() {
        int day = 3;

        String name = switch (day) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6, 7 -> "Weekend";
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        };

        int result = switch (day) {
            case 1, 2, 3, 4, 5 -> {
                System.out.println("Weekday");
                yield day * 10;
            }
            default -> {
                System.out.println("Weekend");
                yield 0;
            }
        };
    }
}

// ============================================================
// TEXT BLOCKS (Java 15+)
// ============================================================

class TextBlockDemo {

    static final String JSON = """
            {
                "name": "Alice",
                "age": 30,
                "languages": ["Java", "Python"]
            }
            """;

    static final String HTML = """
            <html>
                <body>
                    <h1>Hello, World!</h1>
                </body>
            </html>
            """;

    static final String SQL = """
            SELECT u.name, u.email, COUNT(o.id) AS order_count
            FROM users u
            LEFT JOIN orders o ON o.user_id = u.id
            WHERE u.active = true
            GROUP BY u.id
            ORDER BY order_count DESC
            LIMIT 10;
            """;

    static String buildQuery(String table, String condition) {
        return """
                SELECT *
                FROM %s
                WHERE %s
                """.formatted(table, condition);
    }
}

// ============================================================
// VARARGS, STATIC IMPORTS, COERCIONS
// ============================================================

class MiscDemo {

    public static int sum(int... numbers) {
        int total = 0;
        for (int n : numbers) total += n;
        return total;
    }

    public static <T> List<T> listOf(T... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    public static void primitiveCoercions() {
        byte b = 127;
        short s = 32767;
        int i = 2147483647;
        long l = 9223372036854775807L;
        float f = 3.14f;
        double d = 3.141592653589793;
        boolean bool = true;
        char c = 'A';

        int fromByte = b;
        int fromShort = s;
        long fromInt = i;
        float fromLong = l;
        double fromFloat = f;

        byte narrowFromInt = (byte) i;
        int narrowFromLong = (int) l;
        int fromChar = c;
        char fromInt2 = (char) 65;
    }

    public static void autoboxingDemo() {
        Integer boxed = 42;
        int unboxed = boxed;
        Integer sum = Integer.valueOf(1) + Integer.valueOf(2);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        int val = list.get(0);

        Integer a = 100;
        Integer b = 100;
        boolean sameCache = (a == b);

        Integer c = 200;
        Integer d = 200;
        boolean differentCache = (c == d);
        boolean equalValue = c.equals(d);
    }

    public static void bitwiseOperations() {
        int a = 0b1010;
        int b = 0b1100;

        int and = a & b;
        int or = a | b;
        int xor = a ^ b;
        int not = ~a;
        int leftShift = a << 2;
        int rightShift = a >> 1;
        int unsignedRight = a >>> 1;

        int flags = 0;
        int FLAG_READ = 1 << 0;
        int FLAG_WRITE = 1 << 1;
        int FLAG_EXEC = 1 << 2;

        flags |= FLAG_READ;
        flags |= FLAG_WRITE;
        boolean canRead = (flags & FLAG_READ) != 0;
        flags &= ~FLAG_WRITE;
        flags ^= FLAG_EXEC;
    }

    public static void labeledLoops() {
        outer:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2) break outer;
                if (j == 3) continue outer;
                System.out.print(i + "" + j + " ");
            }
        }
    }
}

// ============================================================
// COMPARABLE & COMPARATOR
// ============================================================

class Student implements Comparable<Student>, Cloneable {
    private final String name;
    private final double gpa;
    private final int year;

    public Student(String name, double gpa, int year) {
        this.name = name;
        this.gpa = gpa;
        this.year = year;
    }

    public String getName() { return name; }
    public double getGpa() { return gpa; }
    public int getYear() { return year; }

    @Override
    public int compareTo(Student other) {
        return Double.compare(other.gpa, this.gpa);
    }

    @Override
    protected Student clone() {
        try {
            return (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return name + "(" + gpa + ")";
    }

    public static List<Comparator<Student>> comparators() {
        return Arrays.asList(
            Comparator.comparing(Student::getName),
            Comparator.comparingDouble(Student::getGpa).reversed(),
            Comparator.comparingInt(Student::getYear),
            Comparator.comparing(Student::getName).thenComparingDouble(Student::getGpa),
            Comparator.nullsFirst(Comparator.comparing(Student::getName))
        );
    }
}

// ============================================================
// ITERATOR & ITERABLE
// ============================================================

class Range implements Iterable<Integer> {
    private final int start;
    private final int end;
    private final int step;

    public Range(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int current = start;

            @Override
            public boolean hasNext() {
                return step > 0 ? current < end : current > end;
            }

            @Override
            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                int val = current;
                current += step;
                return val;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        for (int i = start; step > 0 ? i < end : i > end; i += step) {
            action.accept(i);
        }
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return Iterable.super.spliterator();
    }
}

// ============================================================
// STATIC INITIALIZER & INSTANCE INITIALIZER
// ============================================================

class InitializerDemo {
    private static final Map<String, Integer> CONSTANTS;
    private static int instanceCount = 0;
    private final int id;

    static {
        CONSTANTS = new HashMap<>();
        CONSTANTS.put("ONE", 1);
        CONSTANTS.put("TWO", 2);
        CONSTANTS.put("THREE", 3);
        System.out.println("Static initializer");
    }

    {
        id = ++instanceCount;
        System.out.println("Instance initializer: " + id);
    }

    public InitializerDemo() {
        System.out.println("Constructor: " + id);
    }

    public InitializerDemo(String msg) {
        System.out.println("Constructor with msg: " + msg + " id=" + id);
    }
}

// ============================================================
// MAIN
// ============================================================

public class CompleteJava {

    public static void main(String[] args) {
        System.out.println("=== Shapes ===");
        Circle c = new Circle("red", 0, 0, 5);
        Rectangle r = new Rectangle("blue", 1, 1, 4, 3);
        Square sq = new Square("green", 2, 2, 4);
        c.printInfo();
        r.printInfo();
        sq.draw();

        System.out.println("\n=== Generics ===");
        Pair<String, Integer> pair = new Pair<>("age", 30);
        System.out.println(pair.swap());
        GenericStack<Integer> stack = GenericStack.of(1, 2, 3, 4, 5);
        while (!stack.isEmpty()) System.out.print(stack.pop() + " ");

        System.out.println("\n\n=== Streams ===");
        List<Integer> numbers = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());
        numbers.stream()
               .filter(n -> n % 2 == 0)
               .map(n -> n * n)
               .sorted(Comparator.reverseOrder())
               .limit(5)
               .forEach(n -> System.out.print(n + " "));

        System.out.println("\n\n=== Records ===");
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        System.out.println(p1.distanceTo(p2));

        System.out.println("\n=== Pattern Matching ===");
        System.out.println(PatternMatchingDemo.describeObject(42));
        System.out.println(PatternMatchingDemo.describeObject("hello world"));
        System.out.println(PatternMatchingDemo.describeObject(null));

        System.out.println("\n=== Sealed + Expr ===");
        Expr expr = new Add(new Mul(new Num(2), new Num(3)), new Num(4));
        System.out.println(ExprEvaluator.eval(expr));

        System.out.println("\n=== Enums ===");
        for (Season s : Season.values()) {
            System.out.println(s + " -> warm: " + s.isWarm());
        }

        System.out.println("\n=== Range Iterable ===");
        for (int i : new Range(0, 20, 3)) {
            System.out.print(i + " ");
        }

        System.out.println("\n\n=== Date/Time ===");
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
        System.out.println("First day of month: " + today.with(TemporalAdjusters.firstDayOfMonth()));

        System.out.println("\n=== Text Blocks ===");
        System.out.println(TextBlockDemo.buildQuery("users", "active = true"));

        System.out.println("\n=== Varargs ===");
        System.out.println(MiscDemo.sum(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        System.out.println("\n=== Students Comparator ===");
        List<Student> students = Arrays.asList(
            new Student("Alice", 3.9, 2),
            new Student("Bob", 3.7, 3),
            new Student("Charlie", 3.9, 1)
        );
        Collections.sort(students);
        System.out.println(students);
        students.sort(Comparator.comparing(Student::getName));
        System.out.println(students);

        System.out.println("\n=== CompletableFuture ===");
        CompletableFuture.supplyAsync(() -> "Hello")
            .thenApply(s -> s + ", World!")
            .thenAccept(System.out::println)
            .join();
    }
}