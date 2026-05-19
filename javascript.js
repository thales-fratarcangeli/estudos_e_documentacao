var a = 10;
let b = 20;
const c = 30;

var x;
let y = null;
const z = undefined;

const num = 42;
const float = 3.14;
const neg = -7;
const inf = Infinity;
const negInf = -Infinity;
const nan = NaN;
const bigint = 9007199254740991n;
const bigint2 = BigInt(100);

const str = "hello";
const str2 = 'world';
const str3 = `template ${str}`;
const str4 = String(123);

const bool1 = true;
const bool2 = false;
const bool3 = Boolean(0);
const bool4 = Boolean("");
const bool5 = Boolean(null);

const sym1 = Symbol("id");
const sym2 = Symbol("id");
const sym3 = Symbol.for("shared");
const sym4 = Symbol.keyFor(sym3);
const sym5 = Symbol.iterator;
const sym6 = Symbol.toPrimitive;
const sym7 = Symbol.toStringTag;
const sym8 = Symbol.hasInstance;
const sym9 = Symbol.species;
const sym10 = Symbol.asyncIterator;

const obj1 = {};
const obj2 = { name: "Alice", age: 30 };
const obj3 = new Object();
const obj4 = Object.create(null);
const obj5 = Object.create(obj2);

const arr1 = [];
const arr2 = [1, 2, 3];
const arr3 = new Array(3);
const arr4 = new Array(1, 2, 3);
const arr5 = Array.of(1, 2, 3);
const arr6 = Array.from("abc");
const arr7 = Array.from({ length: 3 }, (_, i) => i * 2);
const arr8 = Array.isArray(arr2);

const sum = 5 + 3;
const diff = 5 - 3;
const prod = 5 * 3;
const div = 5 / 3;
const mod = 5 % 3;
const exp = 2 ** 10;
const inc = 5;
let counter = 0;
counter++;
counter--;
++counter;
--counter;

const eq = 5 == "5";
const strict = 5 === "5";
const neq = 5 != "5";
const strictNeq = 5 !== "5";
const gt = 5 > 3;
const lt = 5 < 3;
const gte = 5 >= 5;
const lte = 5 <= 5;

const and = true && false;
const or = true || false;
const not = !true;
const nullish = null ?? "default";
const optional = obj2?.name;
const optDeep = obj2?.address?.city;
const optCall = obj2?.toString?.();

let aa = 10;
aa += 5;
aa -= 5;
aa *= 2;
aa /= 2;
aa %= 3;
aa **= 2;
aa &&= true;
aa ||= false;
aa ??= "fallback";

const bitwiseAnd = 5 & 3;
const bitwiseOr = 5 | 3;
const bitwiseXor = 5 ^ 3;
const bitwiseNot = ~5;
const leftShift = 5 << 1;
const rightShift = 5 >> 1;
const unsignedRight = 5 >>> 1;

const ternary = 5 > 3 ? "yes" : "no";
const typeofNum = typeof 42;
const typeofStr = typeof "hello";
const typeofBool = typeof true;
const typeofUndef = typeof undefined;
const typeofObj = typeof {};
const typeofNull = typeof null;
const typeofFunc = typeof function () {};
const typeofSym = typeof Symbol();
const typeofBig = typeof 1n;
const inOp = "name" in obj2;
const instanceofOp = arr2 instanceof Array;
const voidOp = void 0;
const deleteOp = (() => { const o = { p: 1 }; delete o.p; return o; })();

if (true) {}
if (false) {} else {}
if (1 > 2) {} else if (1 < 2) {} else {}

switch (2) {
  case 1: break;
  case 2: break;
  case 3: break;
  default: break;
}

for (let i = 0; i < 5; i++) {}
for (let i = 5; i > 0; i--) {}
for (let i = 0; i < 10; i += 2) {}

let w = 0;
while (w < 5) { w++; }

let d = 0;
do { d++; } while (d < 5);

const itArr = [1, 2, 3, 4, 5];
for (const val of itArr) {}
for (const char of "hello") {}

const itObj = { a: 1, b: 2, c: 3 };
for (const key in itObj) {}

outer: for (let i = 0; i < 3; i++) {
  inner: for (let j = 0; j < 3; j++) {
    if (j === 1) continue inner;
    if (i === 2) break outer;
  }
}

function declarada() { return "declarada"; }
function comParametros(a, b) { return a + b; }
function comDefault(a = 10, b = 20) { return a + b; }
function comRest(...args) { return args; }
function comDestructuring({ nome, idade }) { return nome + idade; }
function comDestructuringArr([primeiro, segundo]) { return primeiro + segundo; }

const expressao = function () { return "expressao"; };
const nomeada = function minhaFuncao() { return "nomeada"; };
const arrow = () => "arrow";
const arrowParam = (x) => x * 2;
const arrowBlock = (x) => { const r = x * 2; return r; };
const arrowImplicit = x => x * 2;

(function IIFE() {})();
(function (x) { return x; })(42);
(() => {})();

function* geradora() {
  yield 1;
  yield 2;
  yield 3;
}
function* geraInfinito() {
  let n = 0;
  while (true) { yield n++; }
}
function* delegadora() {
  yield* [1, 2, 3];
  yield* geradora();
}
const gen = geradora();
gen.next();
gen.next();
gen.return("fim");
gen.throw(new Error("erro"));

async function assincrona() { return "async"; }
async function comAwait() {
  const r = await Promise.resolve(42);
  return r;
}
async function comTryCatch() {
  try {
    const r = await Promise.reject(new Error("falhou"));
  } catch (e) {
    return e.message;
  }
}
const asyncArrow = async () => await Promise.resolve(1);

function closure() {
  let count = 0;
  return {
    inc: () => ++count,
    dec: () => --count,
    get: () => count,
  };
}
const fechamento = closure();

function currying(a) {
  return function (b) {
    return function (c) {
      return a + b + c;
    };
  };
}
const curriedArrow = a => b => c => a + b + c;

function memoize(fn) {
  const cache = new Map();
  return function (...args) {
    const key = JSON.stringify(args);
    if (cache.has(key)) return cache.get(key);
    const result = fn.apply(this, args);
    cache.set(key, result);
    return result;
  };
}

function compose(...fns) {
  return (x) => fns.reduceRight((acc, fn) => fn(acc), x);
}
function pipe(...fns) {
  return (x) => fns.reduce((acc, fn) => fn(acc), x);
}

function once(fn) {
  let called = false;
  let result;
  return function (...args) {
    if (!called) { called = true; result = fn.apply(this, args); }
    return result;
  };
}

function partial(fn, ...preArgs) {
  return function (...laterArgs) {
    return fn(...preArgs, ...laterArgs);
  };
}

const obj6 = {
  prop: "valor",
  method() { return this.prop; },
  get computed() { return this.prop.toUpperCase(); },
  set computed(v) { this.prop = v.toLowerCase(); },
  ["chave" + "Dinamica"]: true,
  [sym1]: "symbolValue",
};

const shorthand = ((name, age) => ({ name, age }))("Bob", 25);

Object.defineProperty(obj6, "readonly", {
  value: 42,
  writable: false,
  enumerable: true,
  configurable: false,
});

Object.defineProperties(obj6, {
  a: { value: 1, writable: true, enumerable: true, configurable: true },
  b: { value: 2, writable: true, enumerable: true, configurable: true },
});

const descriptor = Object.getOwnPropertyDescriptor(obj6, "prop");
const descriptors = Object.getOwnPropertyDescriptors(obj6);
const keys = Object.keys(obj6);
const values = Object.values(obj6);
const entries = Object.entries(obj6);
const frozen = Object.freeze({ x: 1 });
const sealed = Object.seal({ x: 1 });
const isFrozen = Object.isFrozen(frozen);
const isSealed = Object.isSealed(sealed);
const assigned = Object.assign({}, obj2, { extra: true });
const fromEntries = Object.fromEntries([["a", 1], ["b", 2]]);
const proto = Object.getPrototypeOf(obj2);
Object.setPrototypeOf(obj5, obj2);
const ownNames = Object.getOwnPropertyNames(obj2);
const ownSymbols = Object.getOwnPropertySymbols(obj6);
const hasOwn = Object.hasOwn(obj2, "name");
const is = Object.is(NaN, NaN);
const is2 = Object.is(0, -0);

const proto1 = {
  greet() { return `Hi, I'm ${this.name}`; }
};
const childObj = Object.create(proto1);
childObj.name = "Child";

const { name: nomePessoa, age: idadePessoa } = obj2;
const { name: n1, ...resto } = obj2;
const [primeiro, segundo, ...restArr] = [1, 2, 3, 4, 5];
const [, skip, terceiro] = [1, 2, 3];
const { a: { b: deepVal } = {} } = { a: { b: 99 } };

const spread1 = [...arr2, 4, 5];
const spread2 = { ...obj2, city: "SP" };
const spread3 = [...new Set([1, 1, 2, 2, 3])];

const tagged = ((strings, ...vals) => strings.raw[0])`text\n`;

const strMethods = "Hello World";
strMethods.length;
strMethods.charAt(0);
strMethods.charCodeAt(0);
strMethods.codePointAt(0);
strMethods.indexOf("o");
strMethods.lastIndexOf("o");
strMethods.includes("World");
strMethods.startsWith("Hello");
strMethods.endsWith("World");
strMethods.slice(0, 5);
strMethods.substring(0, 5);
strMethods.toUpperCase();
strMethods.toLowerCase();
strMethods.trim();
strMethods.trimStart();
strMethods.trimEnd();
strMethods.padStart(15, "*");
strMethods.padEnd(15, "*");
strMethods.repeat(2);
strMethods.replace("World", "JS");
strMethods.replaceAll("l", "L");
strMethods.split(" ");
strMethods.split("");
strMethods.concat(" !", " More");
strMethods.at(0);
strMethods.at(-1);
strMethods.match(/\w+/g);
strMethods.matchAll(/\w+/g);
strMethods.search(/World/);
String.fromCharCode(72, 101, 108);
String.fromCodePoint(128514);
strMethods.normalize();
strMethods.normalize("NFC");
strMethods.normalize("NFD");
strMethods.localeCompare("hello world");
strMethods.toLocaleLowerCase();
strMethods.toLocaleUpperCase();

const numMethods = 123.456;
numMethods.toFixed(2);
numMethods.toPrecision(5);
numMethods.toString(16);
numMethods.toString(2);
numMethods.toString(8);
numMethods.valueOf();
numMethods.toExponential(2);
Number.parseInt("42px");
Number.parseFloat("3.14abc");
Number.isInteger(42);
Number.isFinite(Infinity);
Number.isNaN(NaN);
Number.isSafeInteger(9007199254740991);
Number.MAX_SAFE_INTEGER;
Number.MIN_SAFE_INTEGER;
Number.MAX_VALUE;
Number.MIN_VALUE;
Number.POSITIVE_INFINITY;
Number.NEGATIVE_INFINITY;
Number.NaN;
Number.EPSILON;

Math.abs(-5);
Math.ceil(4.1);
Math.floor(4.9);
Math.round(4.5);
Math.trunc(4.9);
Math.sign(-5);
Math.max(1, 2, 3);
Math.min(1, 2, 3);
Math.pow(2, 10);
Math.sqrt(16);
Math.cbrt(27);
Math.hypot(3, 4);
Math.log(Math.E);
Math.log2(8);
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
Math.random();
Math.clz32(1);
Math.fround(1.337);
Math.imul(3, 4);
Math.PI;
Math.E;
Math.LN2;
Math.LN10;
Math.LOG2E;
Math.LOG10E;
Math.SQRT2;
Math.SQRT1_2;

const arrMethods = [3, 1, 4, 1, 5, 9, 2, 6];
arrMethods.push(7);
arrMethods.pop();
arrMethods.unshift(0);
arrMethods.shift();
arrMethods.splice(2, 1);
arrMethods.splice(2, 0, 99);
arrMethods.slice(1, 4);
arrMethods.concat([10, 11]);
arrMethods.join(" - ");
arrMethods.reverse();
arrMethods.sort((a, b) => a - b);
arrMethods.sort((a, b) => b - a);
arrMethods.indexOf(5);
arrMethods.lastIndexOf(1);
arrMethods.includes(9);
arrMethods.find(x => x > 5);
arrMethods.findIndex(x => x > 5);
arrMethods.findLast(x => x > 3);
arrMethods.findLastIndex(x => x > 3);
arrMethods.filter(x => x % 2 === 0);
arrMethods.map(x => x * 2);
arrMethods.reduce((acc, x) => acc + x, 0);
arrMethods.reduceRight((acc, x) => acc + x, 0);
arrMethods.forEach(x => {});
arrMethods.some(x => x > 8);
arrMethods.every(x => x > 0);
arrMethods.flat();
arrMethods.flat(2);
arrMethods.flatMap(x => [x, x * 2]);
arrMethods.fill(0, 2, 5);
arrMethods.copyWithin(0, 3, 6);
arrMethods.entries();
arrMethods.keys();
arrMethods.values();
arrMethods.at(0);
arrMethods.at(-1);
[...arrMethods.entries()];
[...arrMethods.keys()];
[...arrMethods.values()];
Array.from(arrMethods, x => x + 1);

const date1 = new Date();
const date2 = new Date(2024, 0, 15);
const date3 = new Date("2024-01-15T10:30:00");
const date4 = new Date(1705312200000);
date1.getFullYear();
date1.getMonth();
date1.getDate();
date1.getDay();
date1.getHours();
date1.getMinutes();
date1.getSeconds();
date1.getMilliseconds();
date1.getTime();
date1.getTimezoneOffset();
date1.getUTCFullYear();
date1.getUTCMonth();
date1.getUTCDate();
date1.getUTCDay();
date1.getUTCHours();
date1.getUTCMinutes();
date1.getUTCSeconds();
date1.setFullYear(2025);
date1.setMonth(5);
date1.setDate(20);
date1.setHours(12);
date1.setMinutes(30);
date1.setSeconds(0);
date1.setMilliseconds(0);
date1.setTime(Date.now());
date1.toISOString();
date1.toJSON();
date1.toLocaleDateString();
date1.toLocaleTimeString();
date1.toLocaleString();
date1.toDateString();
date1.toTimeString();
date1.toUTCString();
date1.valueOf();
Date.now();
Date.parse("2024-01-15");
Date.UTC(2024, 0, 15, 10, 30, 0);

const re1 = /hello/;
const re2 = /hello/gi;
const re3 = /hello/gimsuy;
const re4 = new RegExp("hello", "gi");
const re5 = /\d+/g;
const re6 = /[a-zA-Z]+/;
const re7 = /^start/;
const re8 = /end$/;
const re9 = /\w+/;
const re10 = /\W+/;
const re11 = /\s+/;
const re12 = /\S+/;
const re13 = /\d+/;
const re14 = /\D+/;
const re15 = /\b\w+\b/g;
const re16 = /(grupo)/;
const re17 = /(?:naoCaptura)/;
const re18 = /(?<nome>\w+)/;
const re19 = /a(?=b)/;
const re20 = /a(?!b)/;
const re21 = /(?<=a)b/;
const re22 = /(?<!a)b/;
const re23 = /a{2,4}/;
const re24 = /a+/;
const re25 = /a*/;
const re26 = /a?/;
re2.test("hello world");
re2.exec("hello world");
re2.source;
re2.flags;
re2.global;
re2.ignoreCase;
re2.multiline;
re2.sticky;
re2.unicode;
re2.lastIndex;
"hello world".match(re2);
"hello world".matchAll(re5);
"hello world".replace(re2, "hi");
"hello world".search(re2);
"hello world".split(/\s+/);

try {
  throw new Error("erro genérico");
} catch (e) {
  e.message;
  e.name;
  e.stack;
} finally {}

try { throw new TypeError("tipo errado"); } catch (e) {}
try { throw new RangeError("fora do range"); } catch (e) {}
try { throw new ReferenceError("ref inválida"); } catch (e) {}
try { throw new SyntaxError("sintaxe inválida"); } catch (e) {}
try { throw new URIError("uri inválida"); } catch (e) {}
try { throw new EvalError("eval inválido"); } catch (e) {}

class Animal {
  #nome;
  #energia = 100;
  static contador = 0;
  static #instancias = 0;

  constructor(nome, especie) {
    this.#nome = nome;
    this.especie = especie;
    Animal.contador++;
    Animal.#instancias++;
  }

  get nome() { return this.#nome; }
  set nome(v) { if (typeof v === "string") this.#nome = v; }
  get energia() { return this.#energia; }

  comer(qtd = 10) {
    this.#energia += qtd;
    return this;
  }
  dormir() {
    this.#energia = 100;
    return this;
  }
  toString() { return `${this.#nome} (${this.especie})`; }
  valueOf() { return this.#energia; }
  [Symbol.toPrimitive](hint) {
    if (hint === "number") return this.#energia;
    if (hint === "string") return this.toString();
    return this.#energia;
  }
  static getContador() { return Animal.contador; }
  static #getInstancias() { return Animal.#instancias; }
  static criar(nome, especie) { return new Animal(nome, especie); }
  *[Symbol.iterator]() {
    yield this.#nome;
    yield this.especie;
    yield this.#energia;
  }
}

class Cachorro extends Animal {
  #raca;

  constructor(nome, raca) {
    super(nome, "Canis lupus familiaris");
    this.#raca = raca;
  }

  get raca() { return this.#raca; }

  latir(vezes = 1) {
    return "Au! ".repeat(vezes).trim();
  }

  toString() {
    return `${super.toString()} - Raça: ${this.#raca}`;
  }
}

class Gato extends Animal {
  constructor(nome) { super(nome, "Felis catus"); }
  miar() { return "Miau!"; }
}

const cachorro = new Cachorro("Rex", "Labrador");
const gato = new Gato("Whiskers");
cachorro instanceof Cachorro;
cachorro instanceof Animal;
cachorro.comer(20).dormir();
[...cachorro];

const map1 = new Map();
map1.set("chave", "valor");
map1.set(42, "numero");
map1.set(obj2, "objeto");
map1.set(sym1, "symbol");
map1.get("chave");
map1.has("chave");
map1.delete("chave");
map1.size;
map1.keys();
map1.values();
map1.entries();
map1.forEach((v, k) => {});
[...map1];
const map2 = new Map([["a", 1], ["b", 2], ["c", 3]]);
map2.clear();

const set1 = new Set();
set1.add(1);
set1.add(2);
set1.add(2);
set1.add(3);
set1.has(2);
set1.delete(2);
set1.size;
set1.keys();
set1.values();
set1.entries();
set1.forEach(v => {});
[...set1];
const set2 = new Set([1, 2, 3, 4, 5]);
const uniao = new Set([...set1, ...set2]);
const intersecao = new Set([...set1].filter(x => set2.has(x)));
const diferenca = new Set([...set2].filter(x => !set1.has(x)));
set2.clear();

const wm1 = new WeakMap();
const wmKey = {};
wm1.set(wmKey, "valor");
wm1.get(wmKey);
wm1.has(wmKey);
wm1.delete(wmKey);

const ws1 = new WeakSet();
const wsObj = {};
ws1.add(wsObj);
ws1.has(wsObj);
ws1.delete(wsObj);

const wr = new WeakRef({ nome: "objeto fraco" });
wr.deref();
wr.deref()?.nome;

const fr = new FinalizationRegistry((valorRetido) => {});
const frObj = { x: 1 };
const frToken = {};
fr.register(frObj, "valor retido", frToken);
fr.unregister(frToken);

const p1 = new Promise((resolve, reject) => {
  resolve("sucesso");
});
const p2 = new Promise((resolve, reject) => {
  reject(new Error("falha"));
});
const p3 = Promise.resolve(42);
const p4 = Promise.reject(new Error("rejeitado"));
const p5 = Promise.all([p3, Promise.resolve(2)]);
const p6 = Promise.race([p3, Promise.resolve(99)]);
const p7 = Promise.allSettled([p3, p4]);
const p8 = Promise.any([p4, p3]);
p3.then(v => v * 2);
p4.catch(e => e.message);
p3.then(v => v).catch(e => e).finally(() => {});
p1.then(v => v).then(v => v).then(v => v);

async function encadeamento() {
  const r1 = await Promise.resolve(1);
  const r2 = await Promise.resolve(r1 + 1);
  const r3 = await Promise.resolve(r2 + 1);
  return r3;
}

async function paralelo() {
  const [a, b, c] = await Promise.all([
    Promise.resolve(1),
    Promise.resolve(2),
    Promise.resolve(3),
  ]);
  return a + b + c;
}

async function* asyncGenerator() {
  yield await Promise.resolve(1);
  yield await Promise.resolve(2);
  yield await Promise.resolve(3);
}

async function consumeAsync() {
  for await (const val of asyncGenerator()) {}
}

const handler = {
  get(target, prop, receiver) {
    return prop in target ? Reflect.get(target, prop, receiver) : `${prop} não existe`;
  },
  set(target, prop, value, receiver) {
    if (typeof value !== "number") throw new TypeError("Apenas números");
    return Reflect.set(target, prop, value, receiver);
  },
  has(target, prop) { return prop in target; },
  deleteProperty(target, prop) { return Reflect.deleteProperty(target, prop); },
  apply(target, thisArg, args) { return Reflect.apply(target, thisArg, args); },
  construct(target, args) { return Reflect.construct(target, args); },
  ownKeys(target) { return Reflect.ownKeys(target); },
  getOwnPropertyDescriptor(target, prop) { return Reflect.getOwnPropertyDescriptor(target, prop); },
  defineProperty(target, prop, desc) { return Reflect.defineProperty(target, prop, desc); },
  getPrototypeOf(target) { return Reflect.getPrototypeOf(target); },
  setPrototypeOf(target, proto) { return Reflect.setPrototypeOf(target, proto); },
  isExtensible(target) { return Reflect.isExtensible(target); },
  preventExtensions(target) { return Reflect.preventExtensions(target); },
};
const proxy = new Proxy({ num: 1 }, handler);
const funcProxy = new Proxy(function () {}, handler);

Reflect.apply(Math.max, null, [1, 2, 3]);
Reflect.construct(Array, [1, 2, 3]);
Reflect.defineProperty({}, "prop", { value: 1 });
Reflect.deleteProperty({ prop: 1 }, "prop");
Reflect.get({ prop: 1 }, "prop");
Reflect.getOwnPropertyDescriptor({ prop: 1 }, "prop");
Reflect.getPrototypeOf({});
Reflect.has({ prop: 1 }, "prop");
Reflect.isExtensible({});
Reflect.ownKeys({ a: 1, b: 2 });
Reflect.preventExtensions({});
Reflect.set({}, "prop", 1);
Reflect.setPrototypeOf({}, null);

const iterable = {
  [Symbol.iterator]() {
    let n = 0;
    return {
      next() {
        return n < 3 ? { value: n++, done: false } : { value: undefined, done: true };
      },
      return(value) { return { value, done: true }; },
      throw(err) { throw err; },
    };
  }
};
[...iterable];
for (const v of iterable) {}

const asyncIterable = {
  [Symbol.asyncIterator]() {
    let n = 0;
    return {
      async next() {
        return n < 3 ? { value: n++, done: false } : { value: undefined, done: true };
      }
    };
  }
};

function* fibonacci() {
  let [a, b] = [0, 1];
  while (true) {
    yield a;
    [a, b] = [b, a + b];
  }
}
const fib = fibonacci();
Array.from({ length: 10 }, () => fib.next().value);

const json1 = JSON.stringify({ a: 1, b: [2, 3], c: null });
const json2 = JSON.stringify({ a: 1 }, null, 2);
const json3 = JSON.stringify({ a: 1 }, ["a"]);
const json4 = JSON.stringify({ a: 1, b: undefined, c: Symbol() });
const parsed1 = JSON.parse('{"a":1,"b":[2,3]}');
const parsed2 = JSON.parse('{"date":"2024-01-01"}', (key, value) => {
  return key === "date" ? new Date(value) : value;
});
JSON.stringify({ a: 1 }, (key, value) => {
  return typeof value === "number" ? value * 2 : value;
});

const intl1 = new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" });
intl1.format(1234.56);
const intl2 = new Intl.DateTimeFormat("pt-BR", { dateStyle: "full" });
intl2.format(new Date());
const intl3 = new Intl.RelativeTimeFormat("pt-BR", { numeric: "auto" });
intl3.format(-1, "day");
const intl4 = new Intl.Collator("pt-BR");
intl4.compare("a", "b");
const intl5 = new Intl.PluralRules("pt-BR");
intl5.select(1);
intl5.select(2);
Intl.getCanonicalLocales("pt-BR");

const typedArr1 = new Int8Array(4);
const typedArr2 = new Uint8Array([1, 2, 3, 4]);
const typedArr3 = new Uint8ClampedArray([255, 300, -1]);
const typedArr4 = new Int16Array(4);
const typedArr5 = new Uint16Array(4);
const typedArr6 = new Int32Array(4);
const typedArr7 = new Uint32Array(4);
const typedArr8 = new Float32Array([1.1, 2.2, 3.3]);
const typedArr9 = new Float64Array([1.1, 2.2]);
const typedArr10 = new BigInt64Array([1n, 2n]);
const typedArr11 = new BigUint64Array([1n, 2n]);

const buf1 = new ArrayBuffer(16);
const view1 = new DataView(buf1);
view1.setInt8(0, 127);
view1.getInt8(0);
view1.setUint8(1, 255);
view1.getUint8(1);
view1.setInt16(2, 1000, true);
view1.getInt16(2, true);
view1.setUint16(4, 60000, true);
view1.getUint16(4, true);
view1.setInt32(6, 100000, true);
view1.getInt32(6, true);
view1.setFloat32(10, 3.14, true);
view1.getFloat32(10, true);
view1.setFloat64(0, 3.14159, true);
view1.getFloat64(0, true);
buf1.byteLength;
buf1.slice(0, 8);
ArrayBuffer.isView(typedArr2);

const enc = new TextEncoder();
const encoded = enc.encode("hello");
const dec = new TextDecoder();
const decoded = dec.decode(encoded);

const url1 = new URL("https://example.com:8080/path?q=1&p=2#section");
url1.href;
url1.protocol;
url1.host;
url1.hostname;
url1.port;
url1.pathname;
url1.search;
url1.searchParams;
url1.hash;
url1.origin;
url1.searchParams.get("q");
url1.searchParams.set("r", "3");
url1.searchParams.append("s", "4");
url1.searchParams.delete("q");
url1.searchParams.has("p");
url1.searchParams.getAll("p");
url1.searchParams.forEach((v, k) => {});
url1.searchParams.keys();
url1.searchParams.values();
url1.searchParams.entries();
url1.searchParams.sort();
url1.toString();
const urlParams = new URLSearchParams("q=1&p=2");
URL.createObjectURL;
URL.revokeObjectURL;

const consoleExamples = () => {
  console.log("log");
  console.error("error");
  console.warn("warn");
  console.info("info");
  console.debug("debug");
  console.dir({});
  console.table([{ a: 1 }, { a: 2 }]);
  console.group("grupo");
  console.groupCollapsed("grupo colapsado");
  console.groupEnd();
  console.time("timer");
  console.timeLog("timer");
  console.timeEnd("timer");
  console.count("label");
  console.countReset("label");
  console.assert(false, "falhou");
  console.trace("rastreio");
  console.clear();
};

function trampoline(fn) {
  return function (...args) {
    let result = fn(...args);
    while (typeof result === "function") result = result();
    return result;
  };
}

function* range(start, end, step = 1) {
  for (let i = start; i < end; i += step) yield i;
}
[...range(0, 10)];
[...range(0, 10, 2)];

function deepClone(obj) {
  if (obj === null || typeof obj !== "object") return obj;
  if (obj instanceof Date) return new Date(obj.getTime());
  if (obj instanceof Array) return obj.map(deepClone);
  if (obj instanceof Map) return new Map([...obj].map(([k, v]) => [deepClone(k), deepClone(v)]));
  if (obj instanceof Set) return new Set([...obj].map(deepClone));
  return Object.fromEntries(Object.entries(obj).map(([k, v]) => [k, deepClone(v)]));
}

function deepEqual(a, b) {
  if (a === b) return true;
  if (typeof a !== typeof b) return false;
  if (typeof a !== "object" || a === null) return false;
  const keysA = Object.keys(a);
  const keysB = Object.keys(b);
  if (keysA.length !== keysB.length) return false;
  return keysA.every(k => deepEqual(a[k], b[k]));
}

function debounce(fn, delay) {
  let timer;
  return function (...args) {
    clearTimeout(timer);
    timer = setTimeout(() => fn.apply(this, args), delay);
  };
}

function throttle(fn, limit) {
  let lastCall = 0;
  return function (...args) {
    const now = Date.now();
    if (now - lastCall >= limit) {
      lastCall = now;
      return fn.apply(this, args);
    }
  };
}

class EventEmitter {
  #events = new Map();

  on(event, listener) {
    if (!this.#events.has(event)) this.#events.set(event, new Set());
    this.#events.get(event).add(listener);
    return this;
  }

  off(event, listener) {
    this.#events.get(event)?.delete(listener);
    return this;
  }

  once(event, listener) {
    const wrapper = (...args) => {
      listener(...args);
      this.off(event, wrapper);
    };
    return this.on(event, wrapper);
  }

  emit(event, ...args) {
    this.#events.get(event)?.forEach(listener => listener(...args));
    return this;
  }

  removeAllListeners(event) {
    if (event) this.#events.delete(event);
    else this.#events.clear();
    return this;
  }

  listenerCount(event) {
    return this.#events.get(event)?.size ?? 0;
  }

  eventNames() {
    return [...this.#events.keys()];
  }
}

class Observable {
  #subscribers = new Set();
  #value;

  constructor(initialValue) { this.#value = initialValue; }

  get value() { return this.#value; }

  set value(newVal) {
    const old = this.#value;
    this.#value = newVal;
    this.#subscribers.forEach(fn => fn(newVal, old));
  }

  subscribe(fn) {
    this.#subscribers.add(fn);
    return () => this.#subscribers.delete(fn);
  }
}

class LinkedList {
  #head = null;
  #size = 0;

  #Node = class {
    constructor(val) {
      this.value = val;
      this.next = null;
    }
  };

  push(val) {
    const node = new this.#Node(val);
    if (!this.#head) { this.#head = node; }
    else {
      let curr = this.#head;
      while (curr.next) curr = curr.next;
      curr.next = node;
    }
    this.#size++;
    return this;
  }

  pop() {
    if (!this.#head) return undefined;
    if (!this.#head.next) {
      const val = this.#head.value;
      this.#head = null;
      this.#size--;
      return val;
    }
    let curr = this.#head;
    while (curr.next.next) curr = curr.next;
    const val = curr.next.value;
    curr.next = null;
    this.#size--;
    return val;
  }

  get size() { return this.#size; }

  *[Symbol.iterator]() {
    let curr = this.#head;
    while (curr) { yield curr.value; curr = curr.next; }
  }

  toArray() { return [...this]; }
}

class Stack {
  #items = [];
  push(item) { this.#items.push(item); return this; }
  pop() { return this.#items.pop(); }
  peek() { return this.#items.at(-1); }
  get size() { return this.#items.length; }
  isEmpty() { return this.#items.length === 0; }
  *[Symbol.iterator]() { yield* [...this.#items].reverse(); }
}

class Queue {
  #items = [];
  enqueue(item) { this.#items.push(item); return this; }
  dequeue() { return this.#items.shift(); }
  peek() { return this.#items[0]; }
  get size() { return this.#items.length; }
  isEmpty() { return this.#items.length === 0; }
  *[Symbol.iterator]() { yield* this.#items; }
}

const Mixin = (Base) => class extends Base {
  greet() { return `Hello from ${this.constructor.name}`; }
};

class Persistable {
  serialize() { return JSON.stringify(this); }
  static deserialize(json) { return Object.assign(new this(), JSON.parse(json)); }
}

class Usuario extends Mixin(Persistable) {
  constructor(nome, email) {
    super();
    this.nome = nome;
    this.email = email;
  }
}

function mixin(target, ...sources) {
  Object.assign(target.prototype, ...sources.map(s => s.prototype ?? s));
  return target;
}

const lazyLoad = new Proxy({}, {
  get(target, prop) {
    if (!(prop in target)) {
      target[prop] = `carregando ${prop}...`;
    }
    return target[prop];
  }
});

const pipeline = (...fns) => (x) => fns.reduce((v, f) => f(v), x);

const match = (value) => ({
  when: (pred, fn) => pred(value) ? { result: fn(value) } : match(value),
  otherwise: (fn) => fn(value),
});

function* zip(...iterables) {
  const iters = iterables.map(it => it[Symbol.iterator]());
  while (true) {
    const results = iters.map(it => it.next());
    if (results.some(r => r.done)) return;
    yield results.map(r => r.value);
  }
}
[...zip([1, 2, 3], ["a", "b", "c"])];

function* take(n, iterable) {
  let count = 0;
  for (const item of iterable) {
    if (count++ >= n) return;
    yield item;
  }
}
[...take(5, fibonacci())];

function* map2(fn, iterable) {
  for (const item of iterable) yield fn(item);
}

function* filter2(pred, iterable) {
  for (const item of iterable) if (pred(item)) yield item;
}

function* flatten(iterable, depth = 1) {
  for (const item of iterable) {
    if (depth > 0 && item != null && typeof item[Symbol.iterator] === "function" && typeof item !== "string") {
      yield* flatten(item, depth - 1);
    } else {
      yield item;
    }
  }
}

const TYPE = Object.freeze({
  LOADING: "LOADING",
  SUCCESS: "SUCCESS",
  ERROR: "ERROR",
  IDLE: "IDLE",
});

function createReducer(initialState, handlers) {
  return function reducer(state = initialState, action) {
    return handlers.hasOwnProperty(action.type)
      ? handlers[action.type](state, action)
      : state;
  };
}

const asyncQueue = async function* (concurrency = 3, tasks = []) {
  const executing = new Set();
  for (const task of tasks) {
    const p = Promise.resolve(task());
    executing.add(p);
    p.finally(() => executing.delete(p));
    if (executing.size >= concurrency) {
      yield await Promise.race(executing);
    }
  }
  while (executing.size > 0) {
    yield await Promise.race(executing);
  }
};

class Result {
  #ok;
  #value;
  #error;
  constructor(ok, value, error) {
    this.#ok = ok;
    this.#value = value;
    this.#error = error;
  }
  static Ok(value) { return new Result(true, value, null); }
  static Err(error) { return new Result(false, null, error); }
  isOk() { return this.#ok; }
  isErr() { return !this.#ok; }
  unwrap() {
    if (!this.#ok) throw this.#error;
    return this.#value;
  }
  unwrapOr(def) { return this.#ok ? this.#value : def; }
  map(fn) { return this.#ok ? Result.Ok(fn(this.#value)) : this; }
  flatMap(fn) { return this.#ok ? fn(this.#value) : this; }
  match({ ok, err }) { return this.#ok ? ok(this.#value) : err(this.#error); }
}

class Option {
  #hasValue;
  #value;
  constructor(hasValue, value) {
    this.#hasValue = hasValue;
    this.#value = value;
  }
  static Some(value) { return new Option(true, value); }
  static None() { return new Option(false, null); }
  isSome() { return this.#hasValue; }
  isNone() { return !this.#hasValue; }
  unwrap() {
    if (!this.#hasValue) throw new Error("None.unwrap");
    return this.#value;
  }
  unwrapOr(def) { return this.#hasValue ? this.#value : def; }
  map(fn) { return this.#hasValue ? Option.Some(fn(this.#value)) : this; }
  flatMap(fn) { return this.#hasValue ? fn(this.#value) : this; }
}

const encodeBase64 = (str) => btoa(unescape(encodeURIComponent(str)));
const decodeBase64 = (str) => decodeURIComponent(escape(atob(str)));
encodeURIComponent("olá mundo");
decodeURIComponent("%C3%A1");
encodeURI("https://example.com/olá");
decodeURI("https://example.com/ol%C3%A1");

parseInt("10", 2);
parseInt("ff", 16);
parseInt("077", 8);
parseFloat("3.14");
isNaN("abc");
isFinite(Infinity);
isFinite(42);
Number.isNaN("abc");
Number.isFinite(Infinity);

const globalRef = globalThis;
globalThis.setTimeout;
globalThis.clearTimeout;
globalThis.setInterval;
globalThis.clearInterval;
globalThis.queueMicrotask;
queueMicrotask(() => {});

const gen2 = (function* () {
  const x = yield 1;
  const y = yield x + 2;
  return x + y;
})();
gen2.next();
gen2.next(10);
gen2.next(20);

const taggedTemplate = (strings, ...values) => {
  return strings.reduce((result, str, i) => {
    return result + str + (values[i] !== undefined ? `[${values[i]}]` : "");
  }, "");
};
const nome = "mundo";
taggedTemplate`Olá ${nome}, como ${42} vai?`;

const obj7 = {
  valueOf() { return 42; },
  toString() { return "objeto"; },
  [Symbol.toPrimitive](hint) {
    if (hint === "number") return 42;
    if (hint === "string") return "objeto";
    return true;
  },
};
+obj7;
`${obj7}`;
obj7 + "";

function assertType(value, type) {
  if (typeof value !== type) throw new TypeError(`Esperado ${type}, recebeu ${typeof value}`);
  return value;
}

function assertInstanceOf(value, Class) {
  if (!(value instanceof Class)) throw new TypeError(`Esperado instância de ${Class.name}`);
  return value;
}

const identity = x => x;
const constant = x => () => x;
const flip = fn => (a, b) => fn(b, a);
const not = fn => (...args) => !fn(...args);
const tap = fn => x => { fn(x); return x; };
const converge = (fn, transformers) => (...args) => fn(...transformers.map(t => t(...args)));
const juxt = (...fns) => (...args) => fns.map(fn => fn(...args));

const chunk = (arr, size) =>
  Array.from({ length: Math.ceil(arr.length / size) }, (_, i) =>
    arr.slice(i * size, i * size + size)
  );

const groupBy = (arr, fn) =>
  arr.reduce((groups, item) => {
    const key = fn(item);
    (groups[key] = groups[key] ?? []).push(item);
    return groups;
  }, {});

const unique = (arr, fn = x => x) => {
  const seen = new Set();
  return arr.filter(item => {
    const key = fn(item);
    if (seen.has(key)) return false;
    seen.add(key);
    return true;
  });
};

const flatten2 = (arr, depth = 1) =>
  depth > 0
    ? arr.reduce((acc, val) => acc.concat(Array.isArray(val) ? flatten2(val, depth - 1) : val), [])
    : arr.slice();

const zip2 = (...arrays) =>
  Array.from({ length: Math.min(...arrays.map(a => a.length)) },
    (_, i) => arrays.map(arr => arr[i]));

const unzip = (pairs) =>
  pairs.reduce(([as, bs], [a, b]) => [[...as, a], [...bs, b]], [[], []]);

const sortBy = (arr, ...fns) =>
  [...arr].sort((a, b) => {
    for (const fn of fns) {
      const va = fn(a), vb = fn(b);
      if (va < vb) return -1;
      if (va > vb) return 1;
    }
    return 0;
  });

function* permutations(arr) {
  if (arr.length <= 1) { yield arr; return; }
  for (let i = 0; i < arr.length; i++) {
    const rest = [...arr.slice(0, i), ...arr.slice(i + 1)];
    for (const perm of permutations(rest)) {
      yield [arr[i], ...perm];
    }
  }
}

function* combinations(arr, k) {
  if (k === 0) { yield []; return; }
  if (arr.length < k) return;
  const [first, ...rest] = arr;
  for (const combo of combinations(rest, k - 1)) yield [first, ...combo];
  yield* combinations(rest, k);
}

const obj8 = {};
const handler2 = {
  get(_, prop) { return prop in String.prototype ? String.prototype[prop] : undefined; }
};
const strProxy = new Proxy("", handler2);

class TypedObject {
  static create(schema) {
    return new Proxy({}, {
      set(target, prop, value) {
        if (!(prop in schema)) throw new Error(`Prop desconhecida: ${prop}`);
        if (typeof value !== schema[prop]) throw new TypeError(`${prop} deve ser ${schema[prop]}`);
        target[prop] = value;
        return true;
      }
    });
  }
}
const typed = TypedObject.create({ nome: "string", idade: "number" });

class PubSub {
  #topics = new Map();
  subscribe(topic, fn) {
    if (!this.#topics.has(topic)) this.#topics.set(topic, new Set());
    this.#topics.get(topic).add(fn);
    return () => this.#topics.get(topic)?.delete(fn);
  }
  publish(topic, data) {
    this.#topics.get(topic)?.forEach(fn => fn(data));
  }
  unsubscribeAll(topic) {
    this.#topics.delete(topic);
  }
}

class StateMachine {
  #state;
  #transitions;
  constructor(initial, transitions) {
    this.#state = initial;
    this.#transitions = transitions;
  }
  get state() { return this.#state; }
  transition(action) {
    const key = `${this.#state}:${action}`;
    if (!this.#transitions.has(key)) throw new Error(`Transição inválida: ${key}`);
    this.#state = this.#transitions.get(key);
    return this;
  }
  can(action) { return this.#transitions.has(`${this.#state}:${action}`); }
}

const fsm = new StateMachine("idle", new Map([
  ["idle:start", "running"],
  ["running:pause", "paused"],
  ["paused:resume", "running"],
  ["running:stop", "idle"],
  ["paused:stop", "idle"],
]));

class Trie {
  #root = {};
  insert(word) {
    let node = this.#root;
    for (const char of word) {
      node[char] = node[char] ?? {};
      node = node[char];
    }
    node.isEnd = true;
  }
  search(word) {
    let node = this.#root;
    for (const char of word) {
      if (!node[char]) return false;
      node = node[char];
    }
    return !!node.isEnd;
  }
  startsWith(prefix) {
    let node = this.#root;
    for (const char of prefix) {
      if (!node[char]) return false;
      node = node[char];
    }
    return true;
  }
}

class MinHeap {
  #heap = [];
  get size() { return this.#heap.length; }
  peek() { return this.#heap[0]; }
  push(val) {
    this.#heap.push(val);
    this.#bubbleUp(this.#heap.length - 1);
  }
  pop() {
    const min = this.#heap[0];
    const last = this.#heap.pop();
    if (this.#heap.length > 0) {
      this.#heap[0] = last;
      this.#sinkDown(0);
    }
    return min;
  }
  #bubbleUp(i) {
    while (i > 0) {
      const parent = Math.floor((i - 1) / 2);
      if (this.#heap[parent] <= this.#heap[i]) break;
      [this.#heap[parent], this.#heap[i]] = [this.#heap[i], this.#heap[parent]];
      i = parent;
    }
  }
  #sinkDown(i) {
    while (true) {
      let min = i;
      const l = 2 * i + 1, r = 2 * i + 2;
      if (l < this.size && this.#heap[l] < this.#heap[min]) min = l;
      if (r < this.size && this.#heap[r] < this.#heap[min]) min = r;
      if (min === i) break;
      [this.#heap[min], this.#heap[i]] = [this.#heap[i], this.#heap[min]];
      i = min;
    }
  }
}

const sort = {
  bubble(arr) {
    const a = [...arr];
    for (let i = 0; i < a.length; i++)
      for (let j = 0; j < a.length - i - 1; j++)
        if (a[j] > a[j + 1]) [a[j], a[j + 1]] = [a[j + 1], a[j]];
    return a;
  },
  selection(arr) {
    const a = [...arr];
    for (let i = 0; i < a.length; i++) {
      let min = i;
      for (let j = i + 1; j < a.length; j++) if (a[j] < a[min]) min = j;
      if (min !== i) [a[i], a[min]] = [a[min], a[i]];
    }
    return a;
  },
  insertion(arr) {
    const a = [...arr];
    for (let i = 1; i < a.length; i++) {
      const key = a[i];
      let j = i - 1;
      while (j >= 0 && a[j] > key) { a[j + 1] = a[j]; j--; }
      a[j + 1] = key;
    }
    return a;
  },
  merge(arr) {
    if (arr.length <= 1) return arr;
    const mid = Math.floor(arr.length / 2);
    const left = sort.merge(arr.slice(0, mid));
    const right = sort.merge(arr.slice(mid));
    const result = [];
    let i = 0, j = 0;
    while (i < left.length && j < right.length)
      result.push(left[i] <= right[j] ? left[i++] : right[j++]);
    return [...result, ...left.slice(i), ...right.slice(j)];
  },
  quick(arr) {
    if (arr.length <= 1) return arr;
    const pivot = arr[Math.floor(arr.length / 2)];
    const left = arr.filter(x => x < pivot);
    const mid = arr.filter(x => x === pivot);
    const right = arr.filter(x => x > pivot);
    return [...sort.quick(left), ...mid, ...sort.quick(right)];
  },
};

const search = {
  binary(arr, target) {
    let lo = 0, hi = arr.length - 1;
    while (lo <= hi) {
      const mid = Math.floor((lo + hi) / 2);
      if (arr[mid] === target) return mid;
      if (arr[mid] < target) lo = mid + 1;
      else hi = mid - 1;
    }
    return -1;
  },
  interpolation(arr, target) {
    let lo = 0, hi = arr.length - 1;
    while (lo <= hi && target >= arr[lo] && target <= arr[hi]) {
      const pos = lo + Math.floor(((target - arr[lo]) / (arr[hi] - arr[lo])) * (hi - lo));
      if (arr[pos] === target) return pos;
      if (arr[pos] < target) lo = pos + 1;
      else hi = pos - 1;
    }
    return -1;
  },
};

"use strict";

function semStrict() {
  return this;
}
function comStrict() {
  "use strict";
  return this;
}

var hoisted = "valor";
function funcaoHoisted() { return "hoisted"; }
hoistTest();
function hoistTest() { return "funcao hoistada antes da declaracao"; }

var varHoisting;
varHoisting = 1;

function exemploArguments() {
  const args = arguments;
  const len = arguments.length;
  const primeiro = arguments[0];
  const arr = Array.from(arguments);
  const arr2 = [...arguments];
  return arguments;
}

function newTargetExemplo() {
  if (new.target) {
    return `chamado com new: ${new.target.name}`;
  }
  return "chamado sem new";
}
class ComNewTarget {
  constructor() {
    new.target.name;
    new.target === ComNewTarget;
  }
}

function funcaoNormal(a, b) { return a + b; }
const callEx = funcaoNormal.call(null, 1, 2);
const callEx2 = funcaoNormal.call({ x: 1 }, 1, 2);
const applyEx = funcaoNormal.apply(null, [1, 2]);
const applyEx2 = funcaoNormal.apply({ x: 1 }, [1, 2]);
const bindEx = funcaoNormal.bind(null, 1);
const bindEx2 = bindEx(2);
const bindEx3 = funcaoNormal.bind(null, 1, 2);
const bindEx4 = bindEx3();

const objComMetodo = {
  valor: 42,
  getValor() { return this.valor; },
};
const metodoSolto = objComMetodo.getValor;
const metodoBound = objComMetodo.getValor.bind(objComMetodo);
objComMetodo.getValor.call({ valor: 99 });
objComMetodo.getValor.apply({ valor: 99 });

funcaoNormal.name;
funcaoNormal.length;
funcaoNormal.toString();
funcaoNormal.prototype;
Function.prototype.call;
Function.prototype.apply;
Function.prototype.bind;

const FuncaoDinamica = new Function("a", "b", "return a + b");
FuncaoDinamica(1, 2);
FuncaoDinamica.length;

function Pessoa(nome, idade) {
  this.nome = nome;
  this.idade = idade;
}
Pessoa.prototype.saudar = function () { return `Oi, sou ${this.nome}`; };
Pessoa.prototype.toString = function () { return `Pessoa(${this.nome}, ${this.idade})`; };
const p = new Pessoa("Alice", 30);
p.saudar();
p.hasOwnProperty("nome");
p.hasOwnProperty("saudar");
p.isPrototypeOf(Object.create(p));
Pessoa.prototype.isPrototypeOf(p);
Object.prototype.isPrototypeOf(p);
p.propertyIsEnumerable("nome");
p.propertyIsEnumerable("saudar");

function Estudante(nome, idade, curso) {
  Pessoa.call(this, nome, idade);
  this.curso = curso;
}
Estudante.prototype = Object.create(Pessoa.prototype);
Estudante.prototype.constructor = Estudante;
Estudante.prototype.estudar = function () { return `${this.nome} estudando ${this.curso}`; };
const e = new Estudante("Bob", 20, "JS");
e instanceof Estudante;
e instanceof Pessoa;
e.saudar();
e.estudar();

const proto2 = {
  tipo: "proto",
  descrever() { return `Sou um ${this.tipo}`; }
};
const filho = Object.create(proto2);
filho.tipo = "filho";
const neto = Object.create(filho);
Object.getPrototypeOf(neto) === filho;
Object.getPrototypeOf(filho) === proto2;
Object.getPrototypeOf(proto2) === Object.prototype;
Object.getPrototypeOf(Object.prototype) === null;

const cadeiaProto = {};
cadeiaProto.__proto__ = { metodoHerdado() { return true; } };

const coercao1 = 1 + "2";
const coercao2 = "3" - 1;
const coercao3 = "3" * "2";
const coercao4 = true + 1;
const coercao5 = false + 1;
const coercao6 = null + 1;
const coercao7 = undefined + 1;
const coercao8 = [] + [];
const coercao9 = [] + {};
const coercao10 = {} + [];
const coercao11 = +"42";
const coercao12 = +true;
const coercao13 = +false;
const coercao14 = +null;
const coercao15 = +undefined;
const coercao16 = +"abc";
const coercao17 = !!"valor";
const coercao18 = !!"";
const coercao19 = !!0;
const coercao20 = !!null;
const coercao21 = !!undefined;
const coercao22 = !!NaN;
const coercao23 = !![];
const coercao24 = !!{};
const coercao25 = "" == false;
const coercao26 = 0 == false;
const coercao27 = null == undefined;
const coercao28 = null == false;
const coercao29 = NaN == NaN;
const coercao30 = [] == false;
const coercao31 = "" == 0;

class ClasseComCampos {
  campoPublico = "publico";
  #campoPrivado = "privado";
  static campoEstatico = "estatico";
  static #campoPrivadoEstatico = "privado estatico";

  static {
    ClasseComCampos.campoEstatico = "inicializado no bloco static";
    ClasseComCampos.#campoPrivadoEstatico = "privado inicializado";
  }

  temCampoPrivado() {
    return #campoPrivado in this;
  }

  static temCampoPrivadoEstatico(obj) {
    return #campoPrivadoEstatico in obj;
  }
}
const instancia = new ClasseComCampos();
#campoPrivado in instancia;

class ErroCustomizado extends Error {
  constructor(mensagem, codigo) {
    super(mensagem);
    this.name = "ErroCustomizado";
    this.codigo = codigo;
    if (Error.captureStackTrace) {
      Error.captureStackTrace(this, ErroCustomizado);
    }
  }
}

class ErroDeValidacao extends ErroCustomizado {
  constructor(campo, mensagem) {
    super(mensagem, "VALIDATION_ERROR");
    this.name = "ErroDeValidacao";
    this.campo = campo;
  }
}

class ErroDeRede extends ErroCustomizado {
  constructor(url, status) {
    super(`Erro ${status} em ${url}`, "NETWORK_ERROR");
    this.name = "ErroDeRede";
    this.url = url;
    this.status = status;
  }
}

try { throw new ErroDeValidacao("email", "Email inválido"); }
catch (e) {
  e instanceof ErroDeValidacao;
  e instanceof ErroCustomizado;
  e instanceof Error;
  e.campo;
  e.codigo;
}

const erroComCause = new Error("erro de alto nível", { cause: new Error("causa raiz") });
erroComCause.cause;
erroComCause.cause.message;

const cloneEstruturado = structuredClone({ a: 1, b: [2, 3], c: new Date() });
const cloneComTransferencia = structuredClone({ buffer: new ArrayBuffer(8) }, { transfer: [new ArrayBuffer(8)] });

const toSorted = [3, 1, 2].toSorted((a, b) => a - b);
const toReversed = [1, 2, 3].toReversed();
const toSpliced = [1, 2, 3, 4].toSpliced(1, 2, 10, 20);
const withArr = [1, 2, 3].with(1, 99);

const groupedBy = Object.groupBy([1, 2, 3, 4, 5, 6], x => x % 2 === 0 ? "par" : "impar");
const groupedByMap = Map.groupBy([1, 2, 3, 4], x => x % 2 === 0 ? "par" : "impar");

const { promise: promiseExterna, resolve: resolveExterno, reject: rejectExterno } = Promise.withResolvers();
resolveExterno(42);

const atPromise = Promise.try(() => JSON.parse('{"a":1}'));

const modulos = {
  exportNomeado: `export const valor = 42;`,
  exportDefault: `export default function() {}`,
  exportTudo: `export { a, b, c };`,
  exportRenomeado: `export { a as alpha };`,
  importNomeado: `import { valor } from './modulo.js';`,
  importDefault: `import MinhaClasse from './modulo.js';`,
  importTudo: `import * as tudo from './modulo.js';`,
  importRenomeado: `import { valor as v } from './modulo.js';`,
  importDinamico: `const mod = await import('./modulo.js');`,
  importMeta: `import.meta.url; import.meta.resolve('./modulo.js');`,
  reexport: `export { valor } from './modulo.js';`,
  reexportDefault: `export { default } from './modulo.js';`,
};

async function importDinamicoSimulado() {
  const mod = await import("./modulo.js").catch(() => ({ default: null }));
  return mod;
}

const timerIds = {
  timeout: setTimeout(() => {}, 1000),
  interval: setInterval(() => {}, 1000),
};
clearTimeout(timerIds.timeout);
clearInterval(timerIds.interval);

const timeoutPromise = (ms) => new Promise(resolve => setTimeout(resolve, ms));
const timeoutPromiseComValor = (ms, val) => new Promise(resolve => setTimeout(() => resolve(val), ms));
const timeoutRejeita = (ms) => new Promise((_, reject) => setTimeout(() => reject(new Error("timeout")), ms));

async function fetchComTimeout(url, ms = 5000) {
  const controller = new AbortController();
  const id = setTimeout(() => controller.abort(), ms);
  try {
    const res = await fetch(url, { signal: controller.signal });
    clearTimeout(id);
    return res;
  } catch (e) {
    clearTimeout(id);
    throw e;
  }
}

async function fetchJSON(url, opcoes = {}) {
  const res = await fetch(url, {
    headers: { "Content-Type": "application/json", ...opcoes.headers },
    ...opcoes,
  });
  if (!res.ok) throw new ErroDeRede(url, res.status);
  return res.json();
}

async function fetchComRetry(url, tentativas = 3, delay = 1000) {
  for (let i = 0; i < tentativas; i++) {
    try {
      return await fetch(url);
    } catch (e) {
      if (i === tentativas - 1) throw e;
      await timeoutPromise(delay * 2 ** i);
    }
  }
}

const fetchHeaders = new Headers({ "Content-Type": "application/json" });
fetchHeaders.set("Authorization", "Bearer token");
fetchHeaders.get("Content-Type");
fetchHeaders.has("Authorization");
fetchHeaders.delete("Authorization");
fetchHeaders.append("Accept", "application/json");
fetchHeaders.forEach((v, k) => {});
fetchHeaders.entries();
fetchHeaders.keys();
fetchHeaders.values();

const req = new Request("https://api.example.com/data", {
  method: "POST",
  headers: fetchHeaders,
  body: JSON.stringify({ key: "value" }),
  mode: "cors",
  credentials: "include",
  cache: "no-cache",
  redirect: "follow",
  referrerPolicy: "no-referrer",
});
req.url;
req.method;
req.headers;
req.clone();

const commaOp1 = (1, 2, 3);
const commaOp2 = (() => { let x = 0; return (x++, x++, x); })();
for (let i = 0, j = 10; i < 5; i++, j--) {}

const labeledBlock = outer: {
  for (let i = 0; i < 5; i++) {
    if (i === 3) break outer;
  }
  "nunca executado";
};

const destructDefault1 = (({ a = 1, b = 2, c = 3 } = {}) => ({ a, b, c }))({ a: 10 });
const destructDefault2 = (([x = 0, y = 0, z = 0] = []) => [x, y, z])([1, 2]);
const destructNested = (({ a: { b: { c = 99 } = {} } = {} } = {}) => c)({});

function* protocoloIteracao() {
  let done = false;
  while (!done) {
    const input = yield;
    if (input === "fim") done = true;
    else yield input * 2;
  }
}
const iter = protocoloIteracao();
iter.next();
iter.next(5);
iter.next(10);
iter.next("fim");

const protoIteravel = {
  data: [1, 2, 3, 4, 5],
  [Symbol.iterator]() {
    let index = 0;
    return {
      next: () => index < this.data.length
        ? { value: this.data[index++], done: false }
        : { value: undefined, done: true },
      [Symbol.iterator]() { return this; },
    };
  }
};

class ColecaoCustomizada {
  #items;
  constructor(...items) { this.#items = items; }

  [Symbol.iterator]() { return this.#items[Symbol.iterator](); }

  get [Symbol.toStringTag]() { return "ColecaoCustomizada"; }

  static [Symbol.hasInstance](instance) {
    return Array.isArray(instance?.items);
  }

  get length() { return this.#items.length; }
  [Symbol.toPrimitive](hint) {
    if (hint === "number") return this.#items.length;
    if (hint === "string") return `Colecao(${this.#items})`;
    return this.#items.length;
  }
}

const valorTruthy = [true, 1, "a", [], {}, () => {}, -1, Infinity, new Date()];
const valorFalsy = [false, 0, -0, 0n, "", null, undefined, NaN];

const propGetSet = Object.create({}, {
  _valor: { value: 0, writable: true, enumerable: false, configurable: true },
  valor: {
    get() { return this._valor; },
    set(v) { this._valor = typeof v === "number" ? v : 0; },
    enumerable: true,
    configurable: true,
  },
});

const objNaoExtensivel = Object.preventExtensions({ a: 1 });
Object.isExtensible(objNaoExtensivel);

const exemploIn = {
  check(obj) {
    return "nome" in obj;
  },
  checkPrivate(instance) {
    return instance instanceof ClasseComCampos;
  },
};

function avaliarTipos(val) {
  return {
    typeOf: typeof val,
    isNull: val === null,
    isUndefined: val === undefined,
    isNullish: val == null,
    isFalsy: !val,
    isTruthy: !!val,
    isNaN: Number.isNaN(val),
    isFinite: Number.isFinite(val),
    isInteger: Number.isInteger(val),
    isArray: Array.isArray(val),
    isObject: typeof val === "object" && val !== null,
    isFunction: typeof val === "function",
    isPrimitive: val !== Object(val),
  };
}

const stringRaw = String.raw`linha1\nlinha2\ttab`;
String.raw`C:\Users\nome\Desktop`;

const weakMemo = new WeakMap();
function memoWeakRef(fn) {
  return function (obj) {
    if (weakMemo.has(obj)) return weakMemo.get(obj);
    const result = fn(obj);
    weakMemo.set(obj, result);
    return result;
  };
}

const cache = new Map();
function withCache(fn, ttl = 5000) {
  return function (...args) {
    const key = JSON.stringify(args);
    const cached = cache.get(key);
    if (cached && Date.now() - cached.time < ttl) return cached.value;
    const value = fn(...args);
    cache.set(key, { value, time: Date.now() });
    return value;
  };
}

const sleep = (ms) => new Promise(r => setTimeout(r, ms));
const retry = async (fn, n = 3) => {
  for (let i = 0; ; i++) {
    try { return await fn(); }
    catch (e) { if (i >= n - 1) throw e; }
  }
};

async function* paginar(fetchPagina, totalPaginas) {
  for (let pagina = 1; pagina <= totalPaginas; pagina++) {
    yield await fetchPagina(pagina);
  }
}

const observable = (obj) => new Proxy(obj, {
  set(target, prop, value, receiver) {
    const old = target[prop];
    const result = Reflect.set(target, prop, value, receiver);
    if (old !== value) console.log(`${prop}: ${old} → ${value}`);
    return result;
  },
});

const imutavel = (obj) => new Proxy(obj, {
  set() { throw new TypeError("Objeto imutável"); },
  deleteProperty() { throw new TypeError("Objeto imutável"); },
});

const validado = (obj, schema) => new Proxy(obj, {
  set(target, prop, value) {
    if (schema[prop] && !schema[prop](value)) throw new TypeError(`Valor inválido para ${prop}`);
    return Reflect.set(target, prop, value);
  },
});

const logado = (obj, nome = "obj") => new Proxy(obj, {
  get(target, prop, receiver) {
    const val = Reflect.get(target, prop, receiver);
    if (typeof val === "function") {
      return function (...args) {
        console.log(`[${nome}] ${prop}(${args.map(a => JSON.stringify(a)).join(", ")})`);
        return val.apply(target, args);
      };
    }
    return val;
  },
});

const memoProxy = new Proxy(function fib(n) {
  if (n <= 1) return n;
  return memoProxy(n - 1) + memoProxy(n - 2);
}, {
  cache: new Map(),
  apply(target, thisArg, [n]) {
    if (this.cache.has(n)) return this.cache.get(n);
    const result = Reflect.apply(target, thisArg, [n]);
    this.cache.set(n, result);
    return result;
  }
});

const exemplosCoercaoAvancada = {
  arrayToNumber: +[],
  arrayVazioParaString: "" + [],
  objetoParaString: "" + {},
  somaArrayObj: [] + {},
  somaObjArray: {} + [],
  subtrairStrings: "10" - "3",
  multiplicarString: "3" * 3,
  dividirString: "9" / "3",
  negativoBool: -true,
  negativoFalse: -false,
  negativoNull: -null,
  negativoString: -"5",
  maiorMenorStrings: "10" > "9",
  maiorMenorMisto: "10" > 9,
};

const pontosFlutante = {
  problema: 0.1 + 0.2,
  comparacaoErrada: 0.1 + 0.2 === 0.3,
  correcaoEpsilon: Math.abs(0.1 + 0.2 - 0.3) < Number.EPSILON,
  correcaoFixed: +(0.1 + 0.2).toFixed(1) === 0.3,
};

const bigintOps = {
  soma: 9007199254740991n + 1n,
  mult: 2n ** 53n,
  div: 10n / 3n,
  mod: 10n % 3n,
  comparacao: 1n == 1,
  comparacaoEstrita: 1n === 1n,
  conversao: Number(42n),
  deBigInt: BigInt(Number.MAX_SAFE_INTEGER),
};