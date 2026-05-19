import os
import sys
import re
import json
import csv
import math
import time
import random
import hashlib
import logging
import inspect
import datetime
import pathlib
import asyncio
import threading
import multiprocessing
import functools
import itertools
import operator
import copy
import pickle
import struct
import socket
import urllib.request
import urllib.parse
import http.client
import collections
import heapq
import bisect
import array
import queue
import io
import textwrap
import string
import unicodedata
import base64
import hmac
import secrets
import uuid
import enum
import dataclasses
import abc
import contextlib
import weakref
import gc
import traceback
import warnings
import unittest
import timeit
import cProfile
import pstats
from typing import (
    Any, Optional, Union, List, Dict, Tuple, Set, FrozenSet,
    Callable, Iterator, Generator, AsyncGenerator, AsyncIterator,
    Type, ClassVar, Final, Literal, TypeVar, Generic, Protocol,
    NamedTuple, TypedDict, Sequence, Mapping, Iterable, Awaitable,
    overload, cast, get_type_hints, TYPE_CHECKING
)
from collections import (
    defaultdict, OrderedDict, Counter, deque, ChainMap, namedtuple, UserDict, UserList, UserString
)
from functools import (
    partial, reduce, lru_cache, cache, cached_property, wraps,
    total_ordering, singledispatch, singledispatchmethod, update_wrapper
)
from itertools import (
    count, cycle, repeat, chain, islice, zip_longest, product,
    permutations, combinations, combinations_with_replacement,
    groupby, accumulate, takewhile, dropwhile, starmap, filterfalse,
    compress, pairwise
)
from contextlib import contextmanager, asynccontextmanager, suppress, redirect_stdout, ExitStack
from dataclasses import dataclass, field, fields, asdict, astuple, replace, InitVar, KW_ONLY
from enum import Enum, IntEnum, Flag, IntFlag, auto, unique
from abc import ABC, abstractmethod
from pathlib import Path
from datetime import date, timedelta, timezone


# ─────────────────────────────────────────────
# TIPOS PRIMITIVOS
# ─────────────────────────────────────────────

inteiro = 42
negativo = -7
grande = 1_000_000
binario = 0b1010
octal = 0o17
hexadecimal = 0xFF
flutuante = 3.14
flutuante_cientifico = 1.5e-3
complexo = 3 + 4j
booleano_true = True
booleano_false = False
nulo = None
texto = "hello"
texto_simples = 'world'
texto_multilinhas = """linha 1
linha 2
linha 3"""
texto_raw = r"C:\Users\nao\escapa"
texto_bytes = b"bytes literais"
texto_fstring = f"valor: {inteiro * 2}"
texto_fstring_formato = f"{flutuante:.2f}"
texto_fstring_expressao = f"{'maiusculo'.upper()}"


# ─────────────────────────────────────────────
# OPERADORES
# ─────────────────────────────────────────────

soma = 10 + 3
subtracao = 10 - 3
multiplicacao = 10 * 3
divisao = 10 / 3
divisao_inteira = 10 // 3
modulo = 10 % 3
potencia = 10 ** 3
negacao = -10

bit_and = 0b1010 & 0b1100
bit_or = 0b1010 | 0b1100
bit_xor = 0b1010 ^ 0b1100
bit_not = ~0b1010
shift_left = 0b0001 << 3
shift_right = 0b1000 >> 2

comp_igual = 10 == 10
comp_diferente = 10 != 5
comp_maior = 10 > 5
comp_menor = 5 < 10
comp_maior_igual = 10 >= 10
comp_menor_igual = 5 <= 10

logico_and = True and False
logico_or = True or False
logico_not = not True

identidade_is = None is None
identidade_is_not = 1 is not None
pertinencia_in = 3 in [1, 2, 3]
pertinencia_not_in = 4 not in [1, 2, 3]

walrus_demo = [y := 10, y + 1]

x = 5
x += 1
x -= 1
x *= 2
x //= 2
x **= 2
x %= 7
x &= 0xFF
x |= 0x01
x ^= 0x10
x <<= 1
x >>= 1


# ─────────────────────────────────────────────
# STRINGS - MÉTODOS
# ─────────────────────────────────────────────

s = "  Olá Mundo Python  "

s.upper()
s.lower()
s.title()
s.capitalize()
s.swapcase()
s.strip()
s.lstrip()
s.rstrip()
s.strip("x")
s.replace("Mundo", "Brasil")
s.replace("l", "L", 1)
s.split()
s.split(",")
s.split(",", 2)
s.rsplit(",", 1)
s.splitlines()
s.join(["a", "b", "c"])
" - ".join(["x", "y", "z"])
s.find("Mundo")
s.find("xyz")
s.rfind("o")
s.index("Olá")
s.rindex("o")
s.count("o")
s.startswith("  Olá")
s.endswith("  ")
s.startswith(("Olá", "  "))
s.endswith(("  ", "!"))
s.isalpha()
s.isdigit()
s.isalnum()
s.isspace()
s.isupper()
s.islower()
s.istitle()
s.isnumeric()
s.isdecimal()
s.isidentifier()
s.isprintable()
s.zfill(30)
s.center(40)
s.center(40, "*")
s.ljust(40)
s.ljust(40, "-")
s.rjust(40)
s.rjust(40, "-")
s.encode("utf-8")
s.encode("utf-8", errors="ignore")
s.encode("ascii", errors="replace")
"abc".encode().decode("utf-8")
"abc".expandtabs(4)
"hello\tthere".expandtabs(8)
"abc" * 3
"abc" + "def"
s.format(name="mundo")
"{0} {1}".format("olá", "mundo")
"{name}".format_map({"name": "Thales"})
s.partition("Mundo")
s.rpartition("o")
s.removeprefix("  Olá")
s.removesuffix("  ")
s.maketrans("aeiou", "AEIOU")
trans_table = str.maketrans({"a": "A", "e": "E"})
"banana".translate(trans_table)
"hello"[1:4]
"hello"[::-1]
ord("A")
chr(65)
len(s)
list(s)
"hello" in s.lower()
repr(s)
ascii(s)


# ─────────────────────────────────────────────
# LISTAS
# ─────────────────────────────────────────────

lst = [1, 2, 3, 4, 5]
lst_mista = [1, "dois", 3.0, True, None, [6, 7]]
lst_vazia = []
lst_range = list(range(10))
lst_range2 = list(range(5, 20, 2))

lst.append(6)
lst.extend([7, 8, 9])
lst.insert(0, 0)
lst.remove(0)
lst.pop()
lst.pop(0)
lst.index(3)
lst.count(3)
lst.sort()
lst.sort(reverse=True)
lst.sort(key=lambda x: -x)
lst.reverse()
copia_lst = lst.copy()
lst.clear()
sorted([3, 1, 2])
sorted([3, 1, 2], reverse=True)
sorted(["banana", "apple"], key=len)
sorted(["banana", "apple"], key=str.lower)
min([3, 1, 2])
max([3, 1, 2])
sum([1, 2, 3])
sum([1, 2, 3], 10)
len([1, 2, 3])
any([False, True, False])
all([True, True, True])
any([])
all([])
enumerate([10, 20, 30])
list(enumerate([10, 20, 30]))
list(enumerate([10, 20, 30], start=1))
zip([1, 2], ["a", "b"])
list(zip([1, 2, 3], ["a", "b", "c"]))
list(zip([1, 2], ["a", "b"], [True, False]))
list(zip_longest([1, 2, 3], ["a", "b"], fillvalue=None))
reversed([1, 2, 3])
list(reversed([1, 2, 3]))
flat = [x for sub in [[1, 2], [3, 4]] for x in sub]
lst2 = [1, 2, 3]
lst2 * 3
lst2 + [4, 5]
lst2[1:3]
lst2[::2]
lst2[-1]
lst2[-3:]
del lst2[0]
2 in lst2
lst2.index(3)


# ─────────────────────────────────────────────
# TUPLAS
# ─────────────────────────────────────────────

tup = (1, 2, 3)
tup_unitaria = (42,)
tup_sem_parenteses = 1, 2, 3
tup_vazia = ()
tup_aninhada = ((1, 2), (3, 4), (5, 6))

tup.count(2)
tup.index(2)
len(tup)
tup[0]
tup[-1]
tup[1:3]
a, b, c = tup
primeiro, *resto = tup
*inicio, ultimo = tup
x1, *meio, x2 = (1, 2, 3, 4, 5)
tup + (4, 5)
tup * 2
3 in tup
tuple([1, 2, 3])
list(tup)


# ─────────────────────────────────────────────
# SETS
# ─────────────────────────────────────────────

s1 = {1, 2, 3, 4}
s2 = {3, 4, 5, 6}
conjunto_vazio = set()
conjunto_de_lista = set([1, 2, 2, 3])
frozenset_ex = frozenset([1, 2, 3])

s1.add(5)
s1.remove(5)
s1.discard(99)
s1.pop()
s1.clear()
s1 = {1, 2, 3, 4}

s1.union(s2)
s1 | s2
s1.intersection(s2)
s1 & s2
s1.difference(s2)
s1 - s2
s1.symmetric_difference(s2)
s1 ^ s2
s1.issubset(s2)
s1 <= s2
s1.issuperset(s2)
s1 >= s2
s1.isdisjoint(s2)
s1.update(s2)
s1.intersection_update(s2)
s1.difference_update(s2)
s1.symmetric_difference_update(s2)
s1 = {1, 2, 3, 4}

copia_s1 = s1.copy()
len(s1)
3 in s1
frozenset_ex | {4, 5}
frozenset_ex & {2, 3}


# ─────────────────────────────────────────────
# DICIONÁRIOS
# ─────────────────────────────────────────────

d = {"nome": "Thales", "idade": 20, "cidade": "Franca"}
d2 = dict(nome="Ana", idade=25)
d3 = dict([("a", 1), ("b", 2)])
d4 = dict.fromkeys(["x", "y", "z"], 0)
d_vazio = {}

d["nome"]
d.get("nome")
d.get("inexistente", "padrão")
d["email"] = "thales@email.com"
d.update({"github": "thales-dev"})
d.update(nome="Thales G.")
del d["email"]
d.pop("github")
d.pop("inexistente", None)
d.popitem()

d = {"nome": "Thales", "idade": 20, "cidade": "Franca"}

d.keys()
list(d.keys())
d.values()
list(d.values())
d.items()
list(d.items())
len(d)
"nome" in d
"email" not in d
d.copy()
d.setdefault("pais", "Brasil")
d.setdefault("nome", "outro")

d_ordenado = {k: d[k] for k in sorted(d)}
invertido = {v: k for k, v in {"a": 1, "b": 2}.items()}
mesclado = {**d, "extra": True}
mesclado2 = d | {"extra": True}
d |= {"novo": "valor"}


# ─────────────────────────────────────────────
# COMPREHENSIONS
# ─────────────────────────────────────────────

quadrados = [x**2 for x in range(10)]
pares = [x for x in range(20) if x % 2 == 0]
pares_quadrados = [x**2 for x in range(20) if x % 2 == 0]
matriz = [[i * j for j in range(1, 4)] for i in range(1, 4)]
flat_matriz = [val for row in matriz for val in row]

set_comp = {x**2 for x in range(10)}
set_filtrado = {x for x in "abracadabra" if x not in "aeiou"}

dict_comp = {k: v for k, v in [("a", 1), ("b", 2)]}
dict_inverso = {v: k for k, v in {"a": 1, "b": 2}.items()}
dict_quadrados = {x: x**2 for x in range(6)}

gen_expr = (x**2 for x in range(10))
gen_soma = sum(x**2 for x in range(10))
gen_any = any(x > 5 for x in range(10))
gen_all = all(x >= 0 for x in range(10))
gen_max = max(x**2 for x in range(-5, 5))


# ─────────────────────────────────────────────
# CONTROLE DE FLUXO
# ─────────────────────────────────────────────

def controle_if(x):
    if x > 0:
        return "positivo"
    elif x < 0:
        return "negativo"
    else:
        return "zero"


def operador_ternario(x):
    return "par" if x % 2 == 0 else "ímpar"


def loops_basicos():
    for i in range(5):
        print(i)

    for i in range(0, 10, 2):
        print(i)

    for i in range(10, 0, -1):
        print(i)

    for item in ["a", "b", "c"]:
        print(item)

    for i, item in enumerate(["x", "y", "z"]):
        print(i, item)

    for a, b in zip([1, 2], ["x", "y"]):
        print(a, b)

    n = 5
    while n > 0:
        n -= 1

    for i in range(10):
        if i == 5:
            break
    else:
        print("loop completou sem break")

    for i in range(10):
        if i % 2 == 0:
            continue
        print(i)

    while True:
        break


def match_case(command):
    match command:
        case "quit":
            return "saindo"
        case "start":
            return "iniciando"
        case str(s) if s.startswith("go"):
            return f"indo para {s[2:]}"
        case _:
            return "desconhecido"


def match_estrutura(ponto):
    match ponto:
        case (0, 0):
            return "origem"
        case (x, 0):
            return f"eixo x em {x}"
        case (0, y):
            return f"eixo y em {y}"
        case (x, y):
            return f"ponto ({x}, {y})"


def match_lista(dados):
    match dados:
        case []:
            return "vazia"
        case [x]:
            return f"um elemento: {x}"
        case [x, y]:
            return f"dois elementos: {x}, {y}"
        case [primeiro, *resto]:
            return f"primeiro: {primeiro}, resto: {resto}"


def match_dict(dados):
    match dados:
        case {"action": "login", "user": usuario}:
            return f"login: {usuario}"
        case {"action": "logout"}:
            return "logout"
        case _:
            return "ação inválida"


# ─────────────────────────────────────────────
# FUNÇÕES
# ─────────────────────────────────────────────

def funcao_simples():
    return 42


def funcao_com_args(a, b, c):
    return a + b + c


def funcao_defaults(a, b=10, c=20):
    return a + b + c


def funcao_args(*args):
    return sum(args)


def funcao_kwargs(**kwargs):
    return kwargs


def funcao_mista(a, b, *args, chave=None, **kwargs):
    return a, b, args, chave, kwargs


def funcao_positional_only(a, b, /, c, d):
    return a + b + c + d


def funcao_keyword_only(a, b, *, c, d):
    return a + b + c + d


def funcao_anotada(x: int, y: int = 0) -> int:
    return x + y


def funcao_retorna_multiplos(x):
    return x, x**2, x**3


def funcao_retorna_nada(x):
    print(x)


def funcao_recursiva(n):
    if n <= 1:
        return 1
    return n * funcao_recursiva(n - 1)


def fib(n):
    if n <= 1:
        return n
    return fib(n - 1) + fib(n - 2)


def fib_memo(n, memo={}):
    if n in memo:
        return memo[n]
    if n <= 1:
        return n
    memo[n] = fib_memo(n - 1, memo) + fib_memo(n - 2, memo)
    return memo[n]


@lru_cache(maxsize=None)
def fib_lru(n):
    if n <= 1:
        return n
    return fib_lru(n - 1) + fib_lru(n - 2)


@cache
def fib_cache(n):
    if n <= 1:
        return n
    return fib_cache(n - 1) + fib_cache(n - 2)


lambda_soma = lambda x, y: x + y
lambda_dobro = lambda x: x * 2
lambda_identidade = lambda x: x
lambda_condicional = lambda x: x if x > 0 else -x

fn_map = list(map(lambda x: x**2, [1, 2, 3, 4, 5]))
fn_filter = list(filter(lambda x: x % 2 == 0, [1, 2, 3, 4, 5]))
fn_reduce = reduce(lambda acc, x: acc + x, [1, 2, 3, 4, 5])
fn_reduce_init = reduce(lambda acc, x: acc + x, [1, 2, 3], 100)

add = functools.partial(funcao_com_args, c=100)
dobrar = partial(map, lambda x: x * 2)

fn_sort_key = sorted(["banana", "apple", "cherry"], key=operator.methodcaller("lower"))
fn_attrgetter = operator.attrgetter("nome")
fn_itemgetter = operator.itemgetter(1)
fn_itemgetter_multi = operator.itemgetter(0, 2)

def compor(f, g):
    return lambda *args, **kwargs: f(g(*args, **kwargs))

dobro = lambda x: x * 2
incremento = lambda x: x + 1
dobro_depois_incremento = compor(dobro, incremento)

def currying(f):
    import inspect
    sig = inspect.signature(f)
    n = len(sig.parameters)
    def curried(*args):
        if len(args) >= n:
            return f(*args)
        return lambda *more: curried(*(args + more))
    return curried


# ─────────────────────────────────────────────
# CLOSURES
# ─────────────────────────────────────────────

def contador():
    n = 0
    def incrementar():
        nonlocal n
        n += 1
        return n
    return incrementar


def multiplicador(fator):
    def multiplicar(x):
        return x * fator
    return multiplicar


def acumulador(inicial=0):
    total = [inicial]
    def adicionar(x):
        total[0] += x
        return total[0]
    def resetar():
        total[0] = inicial
    def obter():
        return total[0]
    return adicionar, resetar, obter


def memoize(f):
    cache = {}
    @wraps(f)
    def wrapper(*args):
        if args not in cache:
            cache[args] = f(*args)
        return cache[args]
    return wrapper


# ─────────────────────────────────────────────
# DECORADORES
# ─────────────────────────────────────────────

def meu_decorator(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        print("antes")
        resultado = func(*args, **kwargs)
        print("depois")
        return resultado
    return wrapper


def decorator_com_args(prefixo="LOG"):
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            print(f"[{prefixo}] chamando {func.__name__}")
            return func(*args, **kwargs)
        return wrapper
    return decorator


def timer(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        inicio = time.perf_counter()
        resultado = func(*args, **kwargs)
        fim = time.perf_counter()
        print(f"{func.__name__} levou {fim - inicio:.6f}s")
        return resultado
    return wrapper


def retry(max_tentativas=3, excecoes=(Exception,), delay=0):
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            for tentativa in range(max_tentativas):
                try:
                    return func(*args, **kwargs)
                except excecoes as e:
                    if tentativa == max_tentativas - 1:
                        raise
                    if delay:
                        time.sleep(delay)
            return None
        return wrapper
    return decorator


def singleton(cls):
    instancias = {}
    @wraps(cls)
    def obter_instancia(*args, **kwargs):
        if cls not in instancias:
            instancias[cls] = cls(*args, **kwargs)
        return instancias[cls]
    return obter_instancia


def validar_tipos(**tipos_esperados):
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            sig = inspect.signature(func)
            bound = sig.bind(*args, **kwargs)
            for nome, valor in bound.arguments.items():
                if nome in tipos_esperados and not isinstance(valor, tipos_esperados[nome]):
                    raise TypeError(f"{nome} deve ser {tipos_esperados[nome]}")
            return func(*args, **kwargs)
        return wrapper
    return decorator


def deprecado(mensagem=""):
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            warnings.warn(
                f"{func.__name__} está deprecado. {mensagem}",
                DeprecationWarning,
                stacklevel=2
            )
            return func(*args, **kwargs)
        return wrapper
    return decorator


class ClasseDecorador:
    def __init__(self, func):
        self.func = func
        self.chamadas = 0
        update_wrapper(self, func)

    def __call__(self, *args, **kwargs):
        self.chamadas += 1
        return self.func(*args, **kwargs)


def decorar_metodos(decorator):
    def class_decorator(cls):
        for nome, metodo in inspect.getmembers(cls, predicate=inspect.isfunction):
            if not nome.startswith("_"):
                setattr(cls, nome, decorator(metodo))
        return cls
    return class_decorator


@meu_decorator
def funcao_decorada():
    return "olá"


@decorator_com_args(prefixo="DEBUG")
def outra_funcao():
    return "resultado"


@timer
@memoize
def operacao_custosa(n):
    return sum(range(n))


# ─────────────────────────────────────────────
# GERADORES
# ─────────────────────────────────────────────

def gerador_simples():
    yield 1
    yield 2
    yield 3


def gerador_range(inicio, fim, passo=1):
    while inicio < fim:
        yield inicio
        inicio += passo


def gerador_fibonacci():
    a, b = 0, 1
    while True:
        yield a
        a, b = b, a + b


def gerador_pipeline(dados):
    for item in dados:
        yield item * 2


def gerador_filtro(dados, pred):
    for item in dados:
        if pred(item):
            yield item


def gerador_infinito():
    n = 0
    while True:
        n += 1
        yield n


def take(n, iteravel):
    return list(islice(iteravel, n))


def gerador_com_send():
    total = 0
    while True:
        valor = yield total
        if valor is None:
            break
        total += valor


def gerador_com_throw():
    try:
        while True:
            yield
    except GeneratorExit:
        print("gerador fechado")
    except ValueError as e:
        print(f"recebeu exceção: {e}")
        yield "após exceção"


def gerador_delegante():
    yield from range(5)
    yield from "abc"
    yield from [10, 20, 30]


def subgerador():
    resultado = yield 1
    yield 2
    return "retorno do sub"


def gerador_principal():
    retorno = yield from subgerador()
    print(f"subgerador retornou: {retorno}")
    yield 3


def gerador_arquivo_linha_a_linha(caminho):
    with open(caminho) as f:
        for linha in f:
            yield linha.rstrip("\n")


class IteradorPersonalizado:
    def __init__(self, limite):
        self.limite = limite
        self.atual = 0

    def __iter__(self):
        return self

    def __next__(self):
        if self.atual >= self.limite:
            raise StopIteration
        valor = self.atual
        self.atual += 1
        return valor


class Range2D:
    def __init__(self, linhas, colunas):
        self.linhas = linhas
        self.colunas = colunas

    def __iter__(self):
        for i in range(self.linhas):
            for j in range(self.colunas):
                yield i, j


# ─────────────────────────────────────────────
# EXCEÇÕES
# ─────────────────────────────────────────────

class ErroBase(Exception):
    pass


class ErroNegocio(ErroBase):
    def __init__(self, mensagem, codigo=None):
        super().__init__(mensagem)
        self.codigo = codigo

    def __str__(self):
        return f"[{self.codigo}] {super().__str__()}"


class ErroValidacao(ErroNegocio):
    pass


class ErroBancoDados(ErroBase):
    def __init__(self, mensagem, query=None, params=None):
        super().__init__(mensagem)
        self.query = query
        self.params = params


class ErroAutorizacao(ErroBase):
    pass


def tratar_excecoes():
    try:
        resultado = 10 / 0
    except ZeroDivisionError as e:
        print(f"divisão por zero: {e}")
    except (TypeError, ValueError) as e:
        print(f"tipo ou valor: {e}")
    except Exception as e:
        print(f"exceção genérica: {type(e).__name__}: {e}")
    else:
        print("sem erros")
    finally:
        print("sempre executa")


def re_lançar():
    try:
        int("abc")
    except ValueError as e:
        raise ErroValidacao("valor inválido", codigo=400) from e


def excecoes_encadeadas():
    try:
        raise ValueError("original")
    except ValueError as e:
        raise RuntimeError("empacotado") from e


def suprimir_excecoes():
    with suppress(FileNotFoundError):
        open("nao_existe.txt")


def grupo_de_excecoes():
    try:
        raise ExceptionGroup("múltiplos erros", [
            ValueError("v1"),
            TypeError("t1"),
            KeyError("k1")
        ])
    except* ValueError as eg:
        print(f"ValueErrors: {eg.exceptions}")
    except* TypeError as eg:
        print(f"TypeErrors: {eg.exceptions}")


def capturar_traceback():
    try:
        raise ValueError("erro")
    except ValueError:
        tb = traceback.format_exc()
        return tb


hierarquia_excecoes = {
    "BaseException": ["SystemExit", "KeyboardInterrupt", "GeneratorExit", "Exception"],
    "Exception": [
        "ArithmeticError", "BufferError", "LookupError",
        "EnvironmentError", "AttributeError", "EOFError",
        "ImportError", "MemoryError", "NameError",
        "ReferenceError", "RuntimeError", "StopIteration",
        "SyntaxError", "SystemError", "TypeError",
        "ValueError", "Warning"
    ]
}


# ─────────────────────────────────────────────
# ORIENTAÇÃO A OBJETOS
# ─────────────────────────────────────────────

class Animal:
    reino = "Animalia"
    _populacao = 0

    def __init__(self, nome: str, idade: int):
        self.nome = nome
        self.idade = idade
        self._energia = 100
        self.__id_interno = id(self)
        Animal._populacao += 1

    def __del__(self):
        Animal._populacao -= 1

    def __str__(self):
        return f"{self.__class__.__name__}({self.nome}, {self.idade} anos)"

    def __repr__(self):
        return f"{self.__class__.__name__}(nome={self.nome!r}, idade={self.idade!r})"

    def __eq__(self, outro):
        if not isinstance(outro, Animal):
            return NotImplemented
        return self.nome == outro.nome and self.idade == outro.idade

    def __ne__(self, outro):
        return not self.__eq__(outro)

    def __lt__(self, outro):
        return self.idade < outro.idade

    def __le__(self, outro):
        return self.idade <= outro.idade

    def __gt__(self, outro):
        return self.idade > outro.idade

    def __ge__(self, outro):
        return self.idade >= outro.idade

    def __hash__(self):
        return hash((self.nome, self.idade))

    def __bool__(self):
        return self._energia > 0

    def __len__(self):
        return self.idade

    def __contains__(self, item):
        return item in self.nome

    def __getitem__(self, key):
        return getattr(self, key)

    def __setitem__(self, key, value):
        setattr(self, key, value)

    def __iter__(self):
        yield self.nome
        yield self.idade

    def __add__(self, outro):
        return self.idade + outro.idade

    def __iadd__(self, valor):
        self._energia += valor
        return self

    def __call__(self, *args, **kwargs):
        return f"{self.nome} foi chamado"

    def __enter__(self):
        return self

    def __exit__(self, tipo, valor, tb):
        return False

    def __getattr__(self, nome):
        raise AttributeError(f"{self.__class__.__name__} não tem '{nome}'")

    def __getattribute__(self, nome):
        return super().__getattribute__(nome)

    def __setattr__(self, nome, valor):
        super().__setattr__(nome, valor)

    def __delattr__(self, nome):
        super().__delattr__(nome)

    def __sizeof__(self):
        return object.__sizeof__(self)

    def __format__(self, spec):
        return f"{self.nome:{spec}}"

    def fazer_som(self):
        raise NotImplementedError

    def comer(self, quantidade=10):
        self._energia += quantidade

    def descansar(self):
        self._energia = 100

    @property
    def energia(self):
        return self._energia

    @energia.setter
    def energia(self, valor):
        if valor < 0:
            raise ValueError("energia não pode ser negativa")
        self._energia = valor

    @energia.deleter
    def energia(self):
        self._energia = 0

    @classmethod
    def criar_filhote(cls, nome):
        return cls(nome, 0)

    @classmethod
    def populacao(cls):
        return cls._populacao

    @staticmethod
    def classificar_por_idade(idade):
        if idade < 1:
            return "filhote"
        elif idade < 5:
            return "jovem"
        return "adulto"


class Cachorro(Animal):
    def __init__(self, nome, idade, raca):
        super().__init__(nome, idade)
        self.raca = raca
        self._truques = []

    def fazer_som(self):
        return f"{self.nome} faz: Au au!"

    def aprender_truque(self, truque):
        self._truques.append(truque)

    def mostrar_truques(self):
        return self._truques.copy()

    def __str__(self):
        return f"Cachorro({self.nome}, {self.raca})"


class Gato(Animal):
    def fazer_som(self):
        return f"{self.nome} faz: Miau!"

    def __str__(self):
        return f"Gato({self.nome})"


class Pato(Animal):
    def fazer_som(self):
        return f"{self.nome} faz: Quack!"

    def voar(self):
        return f"{self.nome} está voando"


class Lobo(Cachorro):
    def __init__(self, nome, idade):
        super().__init__(nome, idade, "Lobo Cinzento")
        self._alcateia = []

    def uivar(self):
        return f"{self.nome} uiva: Auuuuu!"

    def entrar_alcateia(self, membro):
        self._alcateia.append(membro)


class Mamifero(Animal):
    def amamentar(self):
        return "amamentando"


class Voador:
    def voar(self):
        return "voando"

    def altitude(self):
        return 0


class Aquatico:
    def nadar(self):
        return "nadando"

    def profundidade(self):
        return 0


class Ornitorrinco(Mamifero, Voador, Aquatico):
    def __init__(self, nome, idade):
        Mamifero.__init__(self, nome, idade)

    def fazer_som(self):
        return "som estranho"


print(Ornitorrinco.__mro__)


class Singleton:
    _instancia = None

    def __new__(cls, *args, **kwargs):
        if cls._instancia is None:
            cls._instancia = super().__new__(cls)
        return cls._instancia

    def __init__(self, valor=None):
        if not hasattr(self, "_inicializado"):
            self.valor = valor
            self._inicializado = True


class Borg:
    _estado_compartilhado = {}

    def __init__(self):
        self.__dict__ = self._estado_compartilhado


class Observador(ABC):
    @abstractmethod
    def atualizar(self, evento, dados):
        pass


class Sujeito:
    def __init__(self):
        self._observadores: List[Observador] = []
        self._estado = {}

    def assinar(self, obs: Observador):
        self._observadores.append(obs)

    def cancelar(self, obs: Observador):
        self._observadores.remove(obs)

    def notificar(self, evento, dados=None):
        for obs in self._observadores:
            obs.atualizar(evento, dados)

    def set_estado(self, chave, valor):
        self._estado[chave] = valor
        self.notificar("mudança", {chave: valor})


class LogObservador(Observador):
    def atualizar(self, evento, dados):
        print(f"LOG [{evento}]: {dados}")


class MetaClasse(type):
    def __new__(mcs, nome, bases, namespace):
        cls = super().__new__(mcs, nome, bases, namespace)
        return cls

    def __init__(cls, nome, bases, namespace):
        super().__init__(nome, bases, namespace)

    def __call__(cls, *args, **kwargs):
        instancia = super().__call__(*args, **kwargs)
        return instancia

    def __instancecheck__(cls, instancia):
        return super().__instancecheck__(instancia)

    def __subclasscheck__(cls, subclasse):
        return super().__subclasscheck__(subclasse)


class ClasseComMeta(metaclass=MetaClasse):
    pass


class Registro(type):
    _registro = {}

    def __new__(mcs, nome, bases, namespace):
        cls = super().__new__(mcs, nome, bases, namespace)
        mcs._registro[nome] = cls
        return cls


class Plugin(metaclass=Registro):
    pass


class PluginA(Plugin):
    pass


class PluginB(Plugin):
    pass


class Descritores:
    class Validador:
        def __set_name__(self, owner, name):
            self.nome_publico = name
            self.nome_privado = "_" + name

        def __get__(self, obj, objtype=None):
            if obj is None:
                return self
            return getattr(obj, self.nome_privado, None)

        def __set__(self, obj, valor):
            if not isinstance(valor, str):
                raise TypeError(f"{self.nome_publico} deve ser str")
            setattr(obj, self.nome_privado, valor.strip())

        def __delete__(self, obj):
            delattr(obj, self.nome_privado)

    class Positivo:
        def __set_name__(self, owner, name):
            self.name = "_" + name

        def __get__(self, obj, objtype=None):
            if obj is None:
                return self
            return getattr(obj, self.name, 0)

        def __set__(self, obj, valor):
            if valor <= 0:
                raise ValueError("deve ser positivo")
            setattr(obj, self.name, valor)


class Produto:
    nome = Descritores.Validador()
    preco = Descritores.Positivo()

    def __init__(self, nome, preco):
        self.nome = nome
        self.preco = preco


class ProxyClassico:
    def __init__(self, alvo):
        self._alvo = alvo

    def __getattr__(self, nome):
        return getattr(self._alvo, nome)

    def __setattr__(self, nome, valor):
        if nome.startswith("_"):
            super().__setattr__(nome, valor)
        else:
            setattr(self._alvo, nome, valor)


T = TypeVar("T")


class Pilha(Generic[T]):
    def __init__(self):
        self._items: List[T] = []

    def push(self, item: T) -> None:
        self._items.append(item)

    def pop(self) -> T:
        if not self._items:
            raise IndexError("pilha vazia")
        return self._items.pop()

    def peek(self) -> T:
        if not self._items:
            raise IndexError("pilha vazia")
        return self._items[-1]

    def __len__(self) -> int:
        return len(self._items)

    def __bool__(self) -> bool:
        return bool(self._items)

    def __iter__(self) -> Iterator[T]:
        return iter(reversed(self._items))


KT = TypeVar("KT")
VT = TypeVar("VT")


class Mapa(Generic[KT, VT]):
    def __init__(self):
        self._dados: Dict[KT, VT] = {}

    def colocar(self, chave: KT, valor: VT) -> None:
        self._dados[chave] = valor

    def obter(self, chave: KT) -> Optional[VT]:
        return self._dados.get(chave)

    def __contains__(self, chave: KT) -> bool:
        return chave in self._dados


# ─────────────────────────────────────────────
# CLASSES ABSTRATAS & PROTOCOL
# ─────────────────────────────────────────────

class Forma(ABC):
    @abstractmethod
    def area(self) -> float:
        pass

    @abstractmethod
    def perimetro(self) -> float:
        pass

    def descrever(self) -> str:
        return f"área={self.area():.2f}, perímetro={self.perimetro():.2f}"

    @classmethod
    @abstractmethod
    def criar_padrao(cls) -> "Forma":
        pass

    @staticmethod
    @abstractmethod
    def nome_forma() -> str:
        pass


class Circulo(Forma):
    def __init__(self, raio: float):
        self.raio = raio

    def area(self) -> float:
        return math.pi * self.raio ** 2

    def perimetro(self) -> float:
        return 2 * math.pi * self.raio

    @classmethod
    def criar_padrao(cls) -> "Circulo":
        return cls(1.0)

    @staticmethod
    def nome_forma() -> str:
        return "Círculo"


class Retangulo(Forma):
    def __init__(self, largura: float, altura: float):
        self.largura = largura
        self.altura = altura

    def area(self) -> float:
        return self.largura * self.altura

    def perimetro(self) -> float:
        return 2 * (self.largura + self.altura)

    @classmethod
    def criar_padrao(cls) -> "Retangulo":
        return cls(1.0, 1.0)

    @staticmethod
    def nome_forma() -> str:
        return "Retângulo"


class Triangulo(Forma):
    def __init__(self, a: float, b: float, c: float):
        self.a = a
        self.b = b
        self.c = c

    def area(self) -> float:
        s = self.perimetro() / 2
        return math.sqrt(s * (s - self.a) * (s - self.b) * (s - self.c))

    def perimetro(self) -> float:
        return self.a + self.b + self.c

    @classmethod
    def criar_padrao(cls) -> "Triangulo":
        return cls(3.0, 4.0, 5.0)

    @staticmethod
    def nome_forma() -> str:
        return "Triângulo"


class Desenhavel(Protocol):
    def desenhar(self) -> str:
        ...

    def cor(self) -> str:
        ...


class Serializavel(Protocol):
    def serializar(self) -> dict:
        ...

    @classmethod
    def desserializar(cls, dados: dict) -> "Serializavel":
        ...


class Comparable(Protocol):
    def __lt__(self, outro: Any) -> bool:
        ...

    def __eq__(self, outro: Any) -> bool:
        ...


class Gerenciavel(Protocol):
    def __enter__(self) -> "Gerenciavel":
        ...

    def __exit__(self, *args) -> bool:
        ...


# ─────────────────────────────────────────────
# DATACLASSES
# ─────────────────────────────────────────────

@dataclass
class Ponto:
    x: float
    y: float

    def distancia(self, outro: "Ponto") -> float:
        return math.sqrt((self.x - outro.x)**2 + (self.y - outro.y)**2)


@dataclass(order=True, frozen=True)
class Coordenada:
    latitude: float
    longitude: float
    altitude: float = 0.0


@dataclass
class Pessoa:
    nome: str
    idade: int
    email: str = ""
    hobbies: List[str] = field(default_factory=list)
    _id: int = field(default_factory=lambda: random.randint(1000, 9999), repr=False)
    senha: str = field(default="", repr=False, compare=False)

    def __post_init__(self):
        if self.idade < 0:
            raise ValueError("idade não pode ser negativa")
        self.nome = self.nome.strip().title()

    def adicionar_hobby(self, hobby: str):
        self.hobbies.append(hobby)


@dataclass
class Endereco:
    rua: str
    numero: int
    cidade: str
    estado: str
    cep: str = ""


@dataclass
class PessoaComEndereco:
    pessoa: Pessoa
    endereco: Endereco
    _: KW_ONLY
    ativo: bool = True


@dataclass
class NoPilha:
    valor: Any
    proximo: Optional["NoPilha"] = field(default=None, repr=False, compare=False)


p1 = Ponto(1.0, 2.0)
p2 = Ponto(4.0, 6.0)
p1.distancia(p2)
campos = fields(Pessoa)
asdict(p1)
astuple(p1)
p3 = replace(p1, x=10.0)


# ─────────────────────────────────────────────
# ENUMS
# ─────────────────────────────────────────────

class Cor(Enum):
    VERMELHO = 1
    VERDE = 2
    AZUL = 3

    def complementar(self):
        pares = {Cor.VERMELHO: Cor.VERDE, Cor.VERDE: Cor.AZUL, Cor.AZUL: Cor.VERMELHO}
        return pares[self]


class DiaSemana(IntEnum):
    SEGUNDA = 1
    TERCA = 2
    QUARTA = 3
    QUINTA = 4
    SEXTA = 5
    SABADO = 6
    DOMINGO = 7

    @property
    def fim_de_semana(self):
        return self in (DiaSemana.SABADO, DiaSemana.DOMINGO)


class Permissao(Flag):
    NENHUMA = 0
    LEITURA = auto()
    ESCRITA = auto()
    EXECUCAO = auto()
    TUDO = LEITURA | ESCRITA | EXECUCAO


class StatusHTTP(IntEnum):
    OK = 200
    CRIADO = 201
    SEM_CONTEUDO = 204
    NAO_MODIFICADO = 304
    REQUISICAO_INVALIDA = 400
    NAO_AUTORIZADO = 401
    PROIBIDO = 403
    NAO_ENCONTRADO = 404
    ERRO_INTERNO = 500

    @property
    def sucesso(self):
        return 200 <= self < 300

    @property
    def erro_cliente(self):
        return 400 <= self < 500

    @property
    def erro_servidor(self):
        return 500 <= self < 600


@unique
class Naipe(Enum):
    PAUS = "♣"
    OUROS = "♦"
    COPAS = "♥"
    ESPADAS = "♠"


class AutoNome(Enum):
    ALPHA = auto()
    BETA = auto()
    GAMMA = auto()


Cor.VERMELHO
Cor(1)
Cor["VERDE"]
list(Cor)
Cor.VERMELHO.name
Cor.VERMELHO.value
isinstance(Cor.AZUL, Cor)
DiaSemana.SEXTA.fim_de_semana
Permissao.LEITURA | Permissao.ESCRITA
Permissao.TUDO & Permissao.LEITURA
Permissao.LEITURA in Permissao.TUDO


# ─────────────────────────────────────────────
# GERENCIADORES DE CONTEXTO
# ─────────────────────────────────────────────

class GerenciadorArquivo:
    def __init__(self, caminho, modo="r"):
        self.caminho = caminho
        self.modo = modo
        self._arquivo = None

    def __enter__(self):
        self._arquivo = open(self.caminho, self.modo)
        return self._arquivo

    def __exit__(self, tipo, valor, tb):
        if self._arquivo:
            self._arquivo.close()
        return False


class GerenciadorTransacao:
    def __init__(self, conexao):
        self.conexao = conexao

    def __enter__(self):
        self.conexao.begin()
        return self

    def __exit__(self, tipo, valor, tb):
        if tipo is None:
            self.conexao.commit()
        else:
            self.conexao.rollback()
        return False


class Timer:
    def __init__(self, nome=""):
        self.nome = nome
        self.elapsed = 0.0

    def __enter__(self):
        self._inicio = time.perf_counter()
        return self

    def __exit__(self, *args):
        self.elapsed = time.perf_counter() - self._inicio
        if self.nome:
            print(f"{self.nome}: {self.elapsed:.4f}s")
        return False


@contextmanager
def contexto_simples():
    print("entrando")
    try:
        yield "recurso"
    except Exception as e:
        print(f"exceção: {e}")
        raise
    finally:
        print("saindo")


@contextmanager
def contexto_temporario(diretorio=None):
    import tempfile
    orig = os.getcwd()
    tmp = tempfile.mkdtemp() if diretorio is None else diretorio
    try:
        os.chdir(tmp)
        yield Path(tmp)
    finally:
        os.chdir(orig)


@asynccontextmanager
async def contexto_async():
    await asyncio.sleep(0)
    try:
        yield "recurso async"
    finally:
        await asyncio.sleep(0)


def usando_exit_stack():
    with ExitStack() as stack:
        arquivos = [stack.enter_context(open(f"arquivo_{i}.txt", "w")) for i in range(3)]
        for f in arquivos:
            f.write("conteúdo\n")


# ─────────────────────────────────────────────
# ASYNC / AWAIT
# ─────────────────────────────────────────────

async def funcao_async_simples():
    await asyncio.sleep(1)
    return "resultado"


async def buscar_dados(url, delay=0.1):
    await asyncio.sleep(delay)
    return f"dados de {url}"


async def executar_concorrente():
    resultados = await asyncio.gather(
        buscar_dados("url1", 0.1),
        buscar_dados("url2", 0.2),
        buscar_dados("url3", 0.3),
    )
    return resultados


async def executar_com_timeout():
    try:
        resultado = await asyncio.wait_for(funcao_async_simples(), timeout=0.5)
        return resultado
    except asyncio.TimeoutError:
        return "timeout"


async def gerador_async():
    for i in range(5):
        await asyncio.sleep(0.1)
        yield i


async def consumir_gerador_async():
    async for valor in gerador_async():
        print(valor)


async def contexto_async_uso():
    async with contexto_async() as recurso:
        print(recurso)


async def semaphore_exemplo():
    sem = asyncio.Semaphore(3)
    async def tarefa(n):
        async with sem:
            await asyncio.sleep(0.1)
            return n
    return await asyncio.gather(*[tarefa(i) for i in range(10)])


async def fila_async():
    fila = asyncio.Queue(maxsize=5)

    async def produtor():
        for i in range(10):
            await fila.put(i)
        await fila.put(None)

    async def consumidor():
        resultados = []
        while True:
            item = await fila.get()
            if item is None:
                break
            resultados.append(item * 2)
            fila.task_done()
        return resultados

    await asyncio.gather(produtor(), consumidor())


async def evento_async():
    evento = asyncio.Event()

    async def aguardar():
        await evento.wait()
        return "evento ocorreu"

    async def disparar():
        await asyncio.sleep(0.1)
        evento.set()

    await asyncio.gather(aguardar(), disparar())


async def lock_async():
    lock = asyncio.Lock()
    contador = [0]

    async def incrementar():
        async with lock:
            valor = contador[0]
            await asyncio.sleep(0)
            contador[0] = valor + 1

    await asyncio.gather(*[incrementar() for _ in range(100)])
    return contador[0]


async def pipeline_async(dados):
    async def processar(item):
        await asyncio.sleep(0.01)
        return item * 2

    return await asyncio.gather(*[processar(d) for d in dados])


class ClienteAsync:
    def __init__(self):
        self._sessao = None

    async def __aenter__(self):
        self._sessao = {}
        return self

    async def __aexit__(self, *args):
        self._sessao = None

    async def get(self, url):
        await asyncio.sleep(0.01)
        return {"url": url, "status": 200}


# ─────────────────────────────────────────────
# THREADS
# ─────────────────────────────────────────────

def tarefa_thread(nome, delay=0.1):
    time.sleep(delay)
    return f"thread {nome} concluída"


def exemplo_threading():
    threads = []
    for i in range(5):
        t = threading.Thread(target=tarefa_thread, args=(i,), daemon=True)
        threads.append(t)
        t.start()
    for t in threads:
        t.join()


def thread_com_resultado():
    resultados = {}

    def tarefa(n, res):
        res[n] = n ** 2

    threads = [threading.Thread(target=tarefa, args=(i, resultados)) for i in range(5)]
    for t in threads:
        t.start()
    for t in threads:
        t.join()
    return resultados


lock_global = threading.Lock()
rlock = threading.RLock()
evento = threading.Event()
condicao = threading.Condition()
semaforo = threading.Semaphore(3)
semaforo_limitado = threading.BoundedSemaphore(5)
barreira = threading.Barrier(3)

contador_thread_local = threading.local()


class ContadorThread:
    def __init__(self):
        self._valor = 0
        self._lock = threading.Lock()

    def incrementar(self):
        with self._lock:
            self._valor += 1

    @property
    def valor(self):
        with self._lock:
            return self._valor


class ThreadProdutor(threading.Thread):
    def __init__(self, fila, n_itens):
        super().__init__(daemon=True)
        self.fila = fila
        self.n_itens = n_itens

    def run(self):
        for i in range(self.n_itens):
            self.fila.put(i)
        self.fila.put(None)


class ThreadConsumidor(threading.Thread):
    def __init__(self, fila):
        super().__init__(daemon=True)
        self.fila = fila
        self.resultados = []

    def run(self):
        while True:
            item = self.fila.get()
            if item is None:
                break
            self.resultados.append(item * 2)
            self.fila.task_done()


def produtor_consumidor():
    fila = queue.Queue(maxsize=10)
    p = ThreadProdutor(fila, 20)
    c = ThreadConsumidor(fila)
    p.start()
    c.start()
    p.join()
    c.join()
    return c.resultados


# ─────────────────────────────────────────────
# MULTIPROCESSING
# ─────────────────────────────────────────────

def trabalho_cpu(n):
    return sum(i**2 for i in range(n))


def exemplo_multiprocessing():
    with multiprocessing.Pool(processes=4) as pool:
        resultados = pool.map(trabalho_cpu, [100000, 200000, 300000, 400000])
    return resultados


def exemplo_process():
    def worker(fila, n):
        fila.put(n ** 2)

    fila = multiprocessing.Queue()
    processos = [multiprocessing.Process(target=worker, args=(fila, i)) for i in range(5)]
    for p in processos:
        p.start()
    for p in processos:
        p.join()

    resultados = []
    while not fila.empty():
        resultados.append(fila.get())
    return resultados


# ─────────────────────────────────────────────
# TYPING AVANÇADO
# ─────────────────────────────────────────────

T = TypeVar("T")
S = TypeVar("S", str, bytes)
N = TypeVar("N", int, float, complex)
Comparavel = TypeVar("Comparavel", bound="Comparable")

Numero = Union[int, float, complex]
Texto = Union[str, bytes]
Opcional = Optional[str]
ListaDeInts = List[int]
DictStrAny = Dict[str, Any]
TuplaIntStr = Tuple[int, str]
TuplaVariada = Tuple[int, ...]
ConjuntoStr = Set[str]
CallableIntInt = Callable[[int], int]
CallableVarArgs = Callable[..., str]


class PontoNT(NamedTuple):
    x: float
    y: float
    z: float = 0.0


class ConfigTypedDict(TypedDict):
    host: str
    porta: int
    debug: bool


class ConfigParcial(TypedDict, total=False):
    timeout: int
    max_conexoes: int


VERSAO: Final[str] = "1.0.0"
MAX_TENTATIVAS: Final = 3

Modo = Literal["r", "w", "a", "rb", "wb"]


@overload
def processar(x: int) -> int: ...
@overload
def processar(x: str) -> str: ...
def processar(x):
    if isinstance(x, int):
        return x * 2
    return x.upper()


def funcao_generica(lst: List[T]) -> Optional[T]:
    return lst[0] if lst else None


def funcao_com_tipo_retorno(f: Callable[[T], S]) -> Callable[[List[T]], List[S]]:
    def wrapper(lst: List[T]) -> List[S]:
        return [f(item) for item in lst]
    return wrapper


class Repositorio(Generic[T]):
    def __init__(self):
        self._itens: Dict[int, T] = {}
        self._proximo_id = 1

    def salvar(self, item: T) -> int:
        id_ = self._proximo_id
        self._itens[id_] = item
        self._proximo_id += 1
        return id_

    def buscar(self, id_: int) -> Optional[T]:
        return self._itens.get(id_)

    def todos(self) -> List[T]:
        return list(self._itens.values())

    def remover(self, id_: int) -> bool:
        if id_ in self._itens:
            del self._itens[id_]
            return True
        return False


def verificar_tipo(valor: Any, tipo: Type[T]) -> T:
    if not isinstance(valor, tipo):
        raise TypeError(f"esperado {tipo.__name__}, recebido {type(valor).__name__}")
    return cast(T, valor)


hints = get_type_hints(Pessoa)


# ─────────────────────────────────────────────
# COLLECTIONS
# ─────────────────────────────────────────────

dd = defaultdict(list)
dd["chave"].append(1)
dd["chave"].append(2)
dd2 = defaultdict(int)
dd2["contador"] += 1
dd3 = defaultdict(lambda: "valor_padrão")

od = OrderedDict()
od["a"] = 1
od["b"] = 2
od["c"] = 3
od.move_to_end("a")
od.move_to_end("c", last=False)
od.popitem(last=True)
od.popitem(last=False)

cnt = Counter("aababababccc")
cnt.most_common(3)
cnt.most_common()
cnt["a"]
cnt["z"]
cnt.update("aaa")
cnt.subtract("bbb")
cnt.total()
cnt.elements()
c1 = Counter(a=3, b=2)
c2 = Counter(a=1, b=4)
c1 + c2
c1 - c2
c1 & c2
c1 | c2

dq = deque([1, 2, 3])
dq.append(4)
dq.appendleft(0)
dq.extend([5, 6])
dq.extendleft([-1, -2])
dq.pop()
dq.popleft()
dq.rotate(2)
dq.rotate(-2)
dq.reverse()
dq.count(1)
dq.remove(1)
dq.insert(0, 99)
deque(maxlen=5)

cm = ChainMap({"a": 1}, {"b": 2}, {"c": 3, "a": 99})
cm["a"]
cm.maps
cm.parents
cm.new_child({"d": 4})

Ponto3D = namedtuple("Ponto3D", ["x", "y", "z"])
Ponto3D.__new__.__defaults__ = (0.0,)
p3d = Ponto3D(1, 2)
p3d._replace(z=3)
p3d._asdict()
p3d._fields

class MeuDict(UserDict):
    def __setitem__(self, key, value):
        super().__setitem__(str(key), value)

class MinhaLista(UserList):
    def append(self, item):
        if item not in self.data:
            super().append(item)

class MinhaStr(UserString):
    def __add__(self, other):
        return MinhaStr(self.data + str(other))


# ─────────────────────────────────────────────
# ITERTOOLS
# ─────────────────────────────────────────────

list(count(10, 2))[:5]
list(islice(cycle("ABCD"), 10))
list(repeat("x", 5))
list(chain([1, 2], [3, 4], [5, 6]))
list(chain.from_iterable([[1, 2], [3, 4]]))
list(zip_longest([1, 2, 3], ["a", "b"], fillvalue="-"))
list(product("AB", "12"))
list(product(range(2), repeat=3))
list(permutations("ABC", 2))
list(combinations("ABCD", 2))
list(combinations_with_replacement("AB", 3))
list(accumulate([1, 2, 3, 4, 5]))
list(accumulate([1, 2, 3, 4, 5], operator.mul))
list(accumulate([1, 2, 3, 4, 5], initial=10))
list(takewhile(lambda x: x < 5, [1, 2, 3, 4, 5, 6]))
list(dropwhile(lambda x: x < 5, [1, 2, 3, 4, 5, 6]))
list(filterfalse(lambda x: x % 2, range(10)))
list(compress("ABCDEF", [1, 0, 1, 0, 1, 1]))
list(starmap(pow, [(2, 3), (3, 2), (4, 2)]))
list(islice(count(), 10))
list(islice(range(100), 5, 50, 10))

dados = [("Brasil", "São Paulo"), ("Brasil", "Rio"), ("EUA", "NY")]
dados.sort(key=lambda x: x[0])
grupos = {k: list(v) for k, v in groupby(dados, key=lambda x: x[0])}

list(pairwise(range(5)))


# ─────────────────────────────────────────────
# FUNCTOOLS
# ─────────────────────────────────────────────

@lru_cache(maxsize=128)
def custo_alto(n):
    return n ** n % 1000007


custo_alto.cache_info()
custo_alto.cache_clear()


@cache
def cache_ilimitado(n):
    return n * 2


class MinhaClasse:
    @cached_property
    def valor_caro(self):
        return sum(range(10000))


def minha_funcao(a, b, c, d):
    return a + b + c + d


parcial_ab = partial(minha_funcao, 1, 2)
parcial_abc = partial(minha_funcao, 1, 2, 3)

reduce(lambda acc, x: acc * x, range(1, 6))
reduce(operator.add, range(10), 0)


@total_ordering
class Versao:
    def __init__(self, major, minor, patch):
        self.major = major
        self.minor = minor
        self.patch = patch

    def __eq__(self, outro):
        return (self.major, self.minor, self.patch) == (outro.major, outro.minor, outro.patch)

    def __lt__(self, outro):
        return (self.major, self.minor, self.patch) < (outro.major, outro.minor, outro.patch)


@singledispatch
def processar_dado(dado):
    raise NotImplementedError(f"tipo {type(dado)} não suportado")


@processar_dado.register(int)
def _(dado):
    return dado * 2


@processar_dado.register(str)
def _(dado):
    return dado.upper()


@processar_dado.register(list)
def _(dado):
    return [processar_dado(x) for x in dado]


class Formatador:
    @singledispatchmethod
    def formatar(self, valor):
        raise NotImplementedError

    @formatar.register(int)
    def _(self, valor):
        return f"{valor:,}"

    @formatar.register(float)
    def _(self, valor):
        return f"{valor:.2f}"

    @formatar.register(str)
    def _(self, valor):
        return valor.strip()


# ─────────────────────────────────────────────
# ARQUIVOS & IO
# ─────────────────────────────────────────────

def operacoes_arquivo():
    with open("teste.txt", "w", encoding="utf-8") as f:
        f.write("linha 1\n")
        f.write("linha 2\n")
        f.writelines(["linha 3\n", "linha 4\n"])

    with open("teste.txt", "r", encoding="utf-8") as f:
        conteudo = f.read()
        f.seek(0)
        linhas = f.readlines()
        f.seek(0)
        primeira = f.readline()
        f.seek(0)
        for linha in f:
            pass

    with open("teste.txt", "a", encoding="utf-8") as f:
        f.write("linha 5\n")

    with open("binario.bin", "wb") as f:
        f.write(b"\x00\x01\x02\x03")

    with open("binario.bin", "rb") as f:
        dados = f.read()
        f.seek(0)
        byte = f.read(1)

    with open("teste.txt", "r+") as f:
        f.seek(0, 2)
        tamanho = f.tell()


def operacoes_pathlib():
    p = Path("diretorio")
    p.mkdir(exist_ok=True, parents=True)

    arquivo = p / "arquivo.txt"
    arquivo.write_text("conteúdo", encoding="utf-8")
    texto = arquivo.read_text(encoding="utf-8")
    bytes_ = arquivo.read_bytes()
    arquivo.write_bytes(b"bytes")

    arquivo.name
    arquivo.stem
    arquivo.suffix
    arquivo.parent
    arquivo.parts
    arquivo.absolute()
    arquivo.resolve()

    arquivo.exists()
    arquivo.is_file()
    arquivo.is_dir()
    arquivo.stat()
    arquivo.stat().st_size
    arquivo.stat().st_mtime

    arquivo.rename(p / "renomeado.txt")
    novo = p / "renomeado.txt"
    novo.unlink(missing_ok=True)

    list(p.iterdir())
    list(p.glob("*.txt"))
    list(p.rglob("*.py"))

    p.rmdir()

    Path.home()
    Path.cwd()
    Path("/tmp").joinpath("subdir", "arquivo.txt")


def operacoes_csv():
    dados = [["nome", "idade"], ["Thales", 20], ["Ana", 25]]

    with open("dados.csv", "w", newline="", encoding="utf-8") as f:
        writer = csv.writer(f, delimiter=",", quotechar='"')
        writer.writerows(dados)

    with open("dados.csv", "r", encoding="utf-8") as f:
        reader = csv.reader(f)
        for linha in reader:
            pass

    with open("dict_data.csv", "w", newline="", encoding="utf-8") as f:
        campos = ["nome", "idade"]
        writer = csv.DictWriter(f, fieldnames=campos)
        writer.writeheader()
        writer.writerows([{"nome": "Thales", "idade": 20}])

    with open("dict_data.csv", "r", encoding="utf-8") as f:
        reader = csv.DictReader(f)
        for linha in reader:
            pass


def operacoes_json():
    dados = {"nome": "Thales", "idade": 20, "hobbies": ["python", "música"]}

    texto = json.dumps(dados)
    texto_indentado = json.dumps(dados, indent=2, ensure_ascii=False)
    texto_ordenado = json.dumps(dados, indent=2, sort_keys=True)

    recuperado = json.loads(texto)

    with open("dados.json", "w", encoding="utf-8") as f:
        json.dump(dados, f, indent=2, ensure_ascii=False)

    with open("dados.json", "r", encoding="utf-8") as f:
        carregado = json.load(f)

    class Encoder(json.JSONEncoder):
        def default(self, obj):
            if isinstance(obj, datetime.datetime):
                return obj.isoformat()
            if isinstance(obj, set):
                return list(obj)
            return super().default(obj)

    json.dumps({"data": datetime.datetime.now(), "conjunto": {1, 2, 3}}, cls=Encoder)


def operacoes_io():
    buffer = io.StringIO()
    buffer.write("linha 1\n")
    buffer.write("linha 2\n")
    buffer.getvalue()
    buffer.seek(0)
    buffer.read()
    buffer.close()

    bbuffer = io.BytesIO(b"\x00\x01\x02")
    bbuffer.read(1)
    bbuffer.seek(0)
    bbuffer.read()

    with redirect_stdout(io.StringIO()) as capturado:
        print("isso vai para o buffer")
    capturado.getvalue()


# ─────────────────────────────────────────────
# EXPRESSÕES REGULARES
# ─────────────────────────────────────────────

def operacoes_regex():
    padrao_email = r"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}"
    padrao_cpf = r"\d{3}\.\d{3}\.\d{3}-\d{2}"
    padrao_cep = r"\d{5}-\d{3}"
    padrao_tel = r"(?:\+55\s?)?(?:\(?\d{2}\)?\s?)?\d{4,5}[-\s]?\d{4}"
    padrao_data = r"\d{2}/\d{2}/\d{4}"
    padrao_url = r"https?://(?:www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z]{2,6}(?:[-a-zA-Z0-9@:%_+.~#?&/=]*)"

    texto = "Contato: thales@firma.dev.br ou 17-99999-9999"

    re.match(padrao_email, "thales@firma.dev")
    re.search(padrao_email, texto)
    re.findall(padrao_email, texto)
    re.finditer(padrao_email, texto)
    re.sub(padrao_email, "[EMAIL]", texto)
    re.sub(padrao_email, "[EMAIL]", texto, count=1)
    re.subn(padrao_email, "[EMAIL]", texto)
    re.split(r"\s+", texto)
    re.split(r"(\s+)", texto)
    re.fullmatch(r"\d+", "12345")

    flags = re.IGNORECASE | re.MULTILINE | re.DOTALL | re.VERBOSE

    padrao_compilado = re.compile(padrao_email, re.IGNORECASE)
    padrao_compilado.search(texto)
    padrao_compilado.findall(texto)
    padrao_compilado.sub("[EMAIL]", texto)

    padrao_grupos = re.compile(r"(?P<usuario>\w+)@(?P<dominio>[\w.]+)")
    m = padrao_grupos.search("thales@firma.dev")
    if m:
        m.group(0)
        m.group("usuario")
        m.group("dominio")
        m.groups()
        m.groupdict()
        m.start()
        m.end()
        m.span()

    padrao_verbose = re.compile(r"""
        (?P<ano>\d{4})   # ano com 4 dígitos
        [-/]             # separador
        (?P<mes>\d{2})   # mês com 2 dígitos
        [-/]             # separador
        (?P<dia>\d{2})   # dia com 2 dígitos
    """, re.VERBOSE)

    re.compile(r"(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}")
    re.compile(r"(?:https?://)")
    re.compile(r"\b\w+\b")
    re.compile(r"(?<=@)\w+")
    re.compile(r"\w+(?=@)")
    re.compile(r"(?<!@)\w+")
    re.compile(r"\w+(?!@)")

    re.escape("string com.pontos e*asteriscos")


# ─────────────────────────────────────────────
# DATETIME
# ─────────────────────────────────────────────

def operacoes_datetime():
    agora = datetime.datetime.now()
    agora_utc = datetime.datetime.now(tz=timezone.utc)
    hoje = datetime.date.today()
    hora_agora = datetime.datetime.now().time()

    dt = datetime.datetime(2024, 1, 15, 10, 30, 0)
    d = datetime.date(2024, 1, 15)
    t = datetime.time(10, 30, 0, tzinfo=timezone.utc)

    dt.year
    dt.month
    dt.day
    dt.hour
    dt.minute
    dt.second
    dt.microsecond
    dt.weekday()
    dt.isoweekday()
    dt.isoformat()
    dt.strftime("%d/%m/%Y %H:%M:%S")
    dt.strftime("%Y-%m-%dT%H:%M:%S")
    dt.timestamp()
    dt.date()
    dt.time()
    dt.timetuple()
    dt.toordinal()

    datetime.datetime.strptime("15/01/2024", "%d/%m/%Y")
    datetime.datetime.fromtimestamp(1705312200)
    datetime.datetime.utcfromtimestamp(1705312200)
    datetime.datetime.fromordinal(738900)
    datetime.datetime.combine(d, t)
    datetime.datetime.fromisoformat("2024-01-15T10:30:00")

    delta = timedelta(days=30, hours=5, minutes=30)
    delta.days
    delta.seconds
    delta.total_seconds()

    dt + delta
    dt - delta
    dt - datetime.datetime(2024, 1, 1)

    dt.replace(year=2025)
    dt.replace(month=6, day=15)

    fuso_sp = timezone(timedelta(hours=-3))
    dt_sp = dt.astimezone(fuso_sp)
    dt_sp.utcoffset()
    dt_sp.tzname()

    d.isocalendar()
    d.isoweekday()
    d.replace(year=2025)
    d + timedelta(weeks=4)


# ─────────────────────────────────────────────
# MATH & NUMBERS
# ─────────────────────────────────────────────

math.pi
math.e
math.tau
math.inf
math.nan
math.sqrt(16)
math.pow(2, 10)
math.exp(1)
math.log(math.e)
math.log(100, 10)
math.log2(8)
math.log10(1000)
math.floor(3.7)
math.ceil(3.2)
math.trunc(3.9)
math.fabs(-5)
math.factorial(10)
math.gcd(12, 8)
math.lcm(4, 6)
math.comb(10, 3)
math.perm(10, 3)
math.isnan(math.nan)
math.isinf(math.inf)
math.isfinite(3.14)
math.isclose(0.1 + 0.2, 0.3, rel_tol=1e-9)
math.sin(math.pi / 2)
math.cos(0)
math.tan(math.pi / 4)
math.asin(1)
math.acos(1)
math.atan(1)
math.atan2(1, 1)
math.degrees(math.pi)
math.radians(180)
math.sinh(1)
math.cosh(1)
math.tanh(1)
math.hypot(3, 4)
math.hypot(1, 2, 3)
math.dist([0, 0], [3, 4])
math.prod([1, 2, 3, 4, 5])
math.fsum([0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1])
math.remainder(10, 3)
math.copysign(3, -1)
math.frexp(8.0)
math.ldexp(1.0, 3)
math.modf(3.7)
math.nextafter(0.0, math.inf)
math.ulp(1.0)

random.random()
random.uniform(1, 10)
random.randint(1, 10)
random.randrange(0, 100, 5)
random.choice([1, 2, 3, 4, 5])
random.choices([1, 2, 3], weights=[1, 2, 3], k=5)
random.sample([1, 2, 3, 4, 5], 3)
lista_embaralhar = [1, 2, 3, 4, 5]
random.shuffle(lista_embaralhar)
random.seed(42)
random.gauss(0, 1)
random.normalvariate(0, 1)
random.expovariate(1)
random.betavariate(1, 1)
random.gammavariate(1, 1)

int("FF", 16)
int("1010", 2)
int("17", 8)
bin(255)
oct(255)
hex(255)
format(255, "b")
format(255, "o")
format(255, "x")
format(255, "X")
format(255, "#010b")
divmod(17, 5)
abs(-42)
pow(2, 10)
pow(2, 10, 1000)
round(3.14159, 2)
round(3.5)
round(2.5)


# ─────────────────────────────────────────────
# ESTRUTURAS DE DADOS AVANÇADAS
# ─────────────────────────────────────────────

class No:
    def __init__(self, valor, proximo=None):
        self.valor = valor
        self.proximo = proximo


class ListaLigada:
    def __init__(self):
        self._cabeca = None
        self._tamanho = 0

    def inserir_inicio(self, valor):
        self._cabeca = No(valor, self._cabeca)
        self._tamanho += 1

    def inserir_fim(self, valor):
        novo = No(valor)
        if not self._cabeca:
            self._cabeca = novo
        else:
            atual = self._cabeca
            while atual.proximo:
                atual = atual.proximo
            atual.proximo = novo
        self._tamanho += 1

    def remover(self, valor):
        if not self._cabeca:
            return
        if self._cabeca.valor == valor:
            self._cabeca = self._cabeca.proximo
            self._tamanho -= 1
            return
        atual = self._cabeca
        while atual.proximo:
            if atual.proximo.valor == valor:
                atual.proximo = atual.proximo.proximo
                self._tamanho -= 1
                return
            atual = atual.proximo

    def buscar(self, valor):
        atual = self._cabeca
        while atual:
            if atual.valor == valor:
                return True
            atual = atual.proximo
        return False

    def __iter__(self):
        atual = self._cabeca
        while atual:
            yield atual.valor
            atual = atual.proximo

    def __len__(self):
        return self._tamanho


class NoBinario:
    def __init__(self, valor):
        self.valor = valor
        self.esq = None
        self.dir = None


class ArvoreBinariaBusca:
    def __init__(self):
        self._raiz = None

    def inserir(self, valor):
        self._raiz = self._inserir(self._raiz, valor)

    def _inserir(self, no, valor):
        if no is None:
            return NoBinario(valor)
        if valor < no.valor:
            no.esq = self._inserir(no.esq, valor)
        elif valor > no.valor:
            no.dir = self._inserir(no.dir, valor)
        return no

    def buscar(self, valor):
        return self._buscar(self._raiz, valor)

    def _buscar(self, no, valor):
        if no is None or no.valor == valor:
            return no
        if valor < no.valor:
            return self._buscar(no.esq, valor)
        return self._buscar(no.dir, valor)

    def em_ordem(self):
        resultado = []
        self._em_ordem(self._raiz, resultado)
        return resultado

    def _em_ordem(self, no, resultado):
        if no:
            self._em_ordem(no.esq, resultado)
            resultado.append(no.valor)
            self._em_ordem(no.dir, resultado)

    def pre_ordem(self):
        resultado = []
        self._pre_ordem(self._raiz, resultado)
        return resultado

    def _pre_ordem(self, no, resultado):
        if no:
            resultado.append(no.valor)
            self._pre_ordem(no.esq, resultado)
            self._pre_ordem(no.dir, resultado)

    def pos_ordem(self):
        resultado = []
        self._pos_ordem(self._raiz, resultado)
        return resultado

    def _pos_ordem(self, no, resultado):
        if no:
            self._pos_ordem(no.esq, resultado)
            self._pos_ordem(no.dir, resultado)
            resultado.append(no.valor)

    def altura(self):
        return self._altura(self._raiz)

    def _altura(self, no):
        if no is None:
            return 0
        return 1 + max(self._altura(no.esq), self._altura(no.dir))


def heapsort(lst):
    heapq.heapify(lst)
    return [heapq.heappop(lst) for _ in range(len(lst))]


def k_menores(lst, k):
    return heapq.nsmallest(k, lst)


def k_maiores(lst, k):
    return heapq.nlargest(k, lst)


def mesclar_listas_ordenadas(*listas):
    return list(heapq.merge(*listas))


def busca_binaria(lst, alvo):
    idx = bisect.bisect_left(lst, alvo)
    if idx < len(lst) and lst[idx] == alvo:
        return idx
    return -1


def inserir_ordenado(lst, valor):
    bisect.insort(lst, valor)
    return lst


arr_inteiros = array.array("i", [1, 2, 3, 4, 5])
arr_floats = array.array("d", [1.0, 2.0, 3.0])
arr_inteiros.append(6)
arr_inteiros.extend([7, 8, 9])
arr_inteiros.pop()
arr_inteiros.tolist()
bytes(arr_inteiros)


# ─────────────────────────────────────────────
# ALGORITMOS
# ─────────────────────────────────────────────

def bubble_sort(lst):
    lst = lst.copy()
    n = len(lst)
    for i in range(n):
        trocou = False
        for j in range(n - i - 1):
            if lst[j] > lst[j + 1]:
                lst[j], lst[j + 1] = lst[j + 1], lst[j]
                trocou = True
        if not trocou:
            break
    return lst


def selection_sort(lst):
    lst = lst.copy()
    for i in range(len(lst)):
        min_idx = i
        for j in range(i + 1, len(lst)):
            if lst[j] < lst[min_idx]:
                min_idx = j
        lst[i], lst[min_idx] = lst[min_idx], lst[i]
    return lst


def insertion_sort(lst):
    lst = lst.copy()
    for i in range(1, len(lst)):
        chave = lst[i]
        j = i - 1
        while j >= 0 and lst[j] > chave:
            lst[j + 1] = lst[j]
            j -= 1
        lst[j + 1] = chave
    return lst


def merge_sort(lst):
    if len(lst) <= 1:
        return lst
    meio = len(lst) // 2
    esq = merge_sort(lst[:meio])
    dir_ = merge_sort(lst[meio:])
    return _merge(esq, dir_)


def _merge(esq, dir_):
    resultado = []
    i = j = 0
    while i < len(esq) and j < len(dir_):
        if esq[i] <= dir_[j]:
            resultado.append(esq[i])
            i += 1
        else:
            resultado.append(dir_[j])
            j += 1
    return resultado + esq[i:] + dir_[j:]


def quick_sort(lst):
    if len(lst) <= 1:
        return lst
    pivo = lst[len(lst) // 2]
    esq = [x for x in lst if x < pivo]
    meio = [x for x in lst if x == pivo]
    dir_ = [x for x in lst if x > pivo]
    return quick_sort(esq) + meio + quick_sort(dir_)


def busca_linear(lst, alvo):
    for i, val in enumerate(lst):
        if val == alvo:
            return i
    return -1


def busca_binaria_rec(lst, alvo, baixo=0, alto=None):
    if alto is None:
        alto = len(lst) - 1
    if baixo > alto:
        return -1
    meio = (baixo + alto) // 2
    if lst[meio] == alvo:
        return meio
    if lst[meio] < alvo:
        return busca_binaria_rec(lst, alvo, meio + 1, alto)
    return busca_binaria_rec(lst, alvo, baixo, meio - 1)


def bfs(grafo, inicio):
    visitados = set()
    fila = collections.deque([inicio])
    visitados.add(inicio)
    ordem = []
    while fila:
        vertice = fila.popleft()
        ordem.append(vertice)
        for vizinho in grafo.get(vertice, []):
            if vizinho not in visitados:
                visitados.add(vizinho)
                fila.append(vizinho)
    return ordem


def dfs(grafo, inicio, visitados=None):
    if visitados is None:
        visitados = set()
    visitados.add(inicio)
    ordem = [inicio]
    for vizinho in grafo.get(inicio, []):
        if vizinho not in visitados:
            ordem += dfs(grafo, vizinho, visitados)
    return ordem


def dfs_iterativo(grafo, inicio):
    visitados = set()
    pilha = [inicio]
    ordem = []
    while pilha:
        vertice = pilha.pop()
        if vertice not in visitados:
            visitados.add(vertice)
            ordem.append(vertice)
            pilha.extend(grafo.get(vertice, []))
    return ordem


def dijkstra(grafo_pesos, inicio):
    distancias = {v: float("inf") for v in grafo_pesos}
    distancias[inicio] = 0
    heap = [(0, inicio)]
    while heap:
        dist_atual, u = heapq.heappop(heap)
        if dist_atual > distancias[u]:
            continue
        for v, peso in grafo_pesos[u].items():
            nova_dist = distancias[u] + peso
            if nova_dist < distancias[v]:
                distancias[v] = nova_dist
                heapq.heappush(heap, (nova_dist, v))
    return distancias


def programacao_dinamica_mochila(pesos, valores, capacidade):
    n = len(pesos)
    dp = [[0] * (capacidade + 1) for _ in range(n + 1)]
    for i in range(1, n + 1):
        for w in range(capacidade + 1):
            dp[i][w] = dp[i - 1][w]
            if pesos[i - 1] <= w:
                dp[i][w] = max(dp[i][w], dp[i - 1][w - pesos[i - 1]] + valores[i - 1])
    return dp[n][capacidade]


def maior_subsequencia_crescente(seq):
    if not seq:
        return 0
    dp = [1] * len(seq)
    for i in range(1, len(seq)):
        for j in range(i):
            if seq[j] < seq[i]:
                dp[i] = max(dp[i], dp[j] + 1)
    return max(dp)


def distancia_edicao(s1, s2):
    m, n = len(s1), len(s2)
    dp = [[0] * (n + 1) for _ in range(m + 1)]
    for i in range(m + 1):
        dp[i][0] = i
    for j in range(n + 1):
        dp[0][j] = j
    for i in range(1, m + 1):
        for j in range(1, n + 1):
            if s1[i - 1] == s2[j - 1]:
                dp[i][j] = dp[i - 1][j - 1]
            else:
                dp[i][j] = 1 + min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
    return dp[m][n]


# ─────────────────────────────────────────────
# HASH, CRYPTO, SECRETS
# ─────────────────────────────────────────────

def operacoes_hash():
    hashlib.md5(b"dados").hexdigest()
    hashlib.sha1(b"dados").hexdigest()
    hashlib.sha256(b"dados").hexdigest()
    hashlib.sha512(b"dados").hexdigest()
    hashlib.sha3_256(b"dados").hexdigest()
    hashlib.blake2b(b"dados").hexdigest()
    hashlib.blake2s(b"dados").hexdigest()

    h = hashlib.sha256()
    h.update(b"parte 1")
    h.update(b"parte 2")
    h.hexdigest()
    h.digest()
    h.copy()

    hashlib.pbkdf2_hmac("sha256", b"senha", b"salt", 100000)
    hashlib.scrypt(b"senha", salt=b"salt", n=16384, r=8, p=1)

    hmac.new(b"chave", b"mensagem", hashlib.sha256).hexdigest()

    secrets.token_bytes(32)
    secrets.token_hex(32)
    secrets.token_urlsafe(32)
    secrets.randbits(256)
    secrets.choice([1, 2, 3, 4, 5])
    secrets.SystemRandom().random()

    base64.b64encode(b"dados")
    base64.b64decode(b"ZGFkb3M=")
    base64.urlsafe_b64encode(b"dados")
    base64.urlsafe_b64decode(b"ZGFkb3M=")
    base64.b32encode(b"dados")
    base64.b16encode(b"dados")

    str(uuid.uuid4())
    str(uuid.uuid1())
    str(uuid.uuid3(uuid.NAMESPACE_DNS, "firma.dev"))
    str(uuid.uuid5(uuid.NAMESPACE_DNS, "firma.dev"))


# ─────────────────────────────────────────────
# LOGGING
# ─────────────────────────────────────────────

def configurar_logging():
    logger = logging.getLogger("minha_app")
    logger.setLevel(logging.DEBUG)

    handler_console = logging.StreamHandler()
    handler_console.setLevel(logging.DEBUG)

    handler_arquivo = logging.FileHandler("app.log")
    handler_arquivo.setLevel(logging.WARNING)

    formatter = logging.Formatter(
        "%(asctime)s - %(name)s - %(levelname)s - %(message)s",
        datefmt="%Y-%m-%d %H:%M:%S"
    )
    handler_console.setFormatter(formatter)
    handler_arquivo.setFormatter(formatter)

    logger.addHandler(handler_console)
    logger.addHandler(handler_arquivo)

    logger.debug("mensagem de debug")
    logger.info("mensagem de info")
    logger.warning("mensagem de aviso")
    logger.error("mensagem de erro")
    logger.critical("mensagem crítica")

    try:
        raise ValueError("erro")
    except ValueError:
        logger.exception("erro com traceback")

    logger.log(logging.INFO, "nível customizado")

    child = logger.getChild("submodulo")
    child.info("log do filho")

    logging.basicConfig(
        level=logging.DEBUG,
        format="%(levelname)s:%(name)s:%(message)s",
        handlers=[logging.StreamHandler()]
    )

    return logger


# ─────────────────────────────────────────────
# TESTES
# ─────────────────────────────────────────────

class TestesExemplo(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.dados_compartilhados = [1, 2, 3]

    @classmethod
    def tearDownClass(cls):
        pass

    def setUp(self):
        self.lista = [1, 2, 3, 4, 5]
        self.dicionario = {"a": 1}

    def tearDown(self):
        pass

    def test_assert_igual(self):
        self.assertEqual(2 + 2, 4)
        self.assertNotEqual(2 + 2, 5)

    def test_assert_booleano(self):
        self.assertTrue(True)
        self.assertFalse(False)

    def test_assert_none(self):
        self.assertIsNone(None)
        self.assertIsNotNone(42)

    def test_assert_in(self):
        self.assertIn(1, [1, 2, 3])
        self.assertNotIn(4, [1, 2, 3])

    def test_assert_instancia(self):
        self.assertIsInstance(42, int)
        self.assertNotIsInstance("str", int)

    def test_assert_raises(self):
        with self.assertRaises(ValueError):
            int("abc")

    def test_assert_raises_regex(self):
        with self.assertRaisesRegex(ValueError, "invalid literal"):
            int("abc")

    def test_assert_quase_igual(self):
        self.assertAlmostEqual(0.1 + 0.2, 0.3, places=7)
        self.assertNotAlmostEqual(0.1, 0.2)

    def test_assert_maior_menor(self):
        self.assertGreater(5, 3)
        self.assertGreaterEqual(5, 5)
        self.assertLess(3, 5)
        self.assertLessEqual(3, 3)

    def test_assert_lista(self):
        self.assertListEqual([1, 2, 3], [1, 2, 3])
        self.assertTupleEqual((1, 2), (1, 2))
        self.assertSetEqual({1, 2, 3}, {3, 2, 1})
        self.assertDictEqual({"a": 1}, {"a": 1})

    def test_assert_logs(self):
        with self.assertLogs("test", level="INFO") as cm:
            logging.getLogger("test").info("mensagem")
        self.assertIn("INFO:test:mensagem", cm.output)

    @unittest.skip("pular este teste")
    def test_pulado(self):
        pass

    @unittest.skipIf(sys.platform == "win32", "não suportado no Windows")
    def test_condicional(self):
        pass

    @unittest.expectedFailure
    def test_falha_esperada(self):
        self.assertEqual(1, 2)

    def test_subtestes(self):
        casos = [(1, 1, 2), (2, 3, 5), (10, 5, 15)]
        for a, b, esperado in casos:
            with self.subTest(a=a, b=b):
                self.assertEqual(a + b, esperado)


def suite_testes():
    suite = unittest.TestSuite()
    suite.addTest(TestesExemplo("test_assert_igual"))
    return suite


# ─────────────────────────────────────────────
# COPY & PICKLE
# ─────────────────────────────────────────────

def operacoes_copia():
    original = {"lista": [1, 2, 3], "aninhado": {"a": 1}}

    copia_rasa = original.copy()
    copia_rasa2 = copy.copy(original)
    copia_profunda = copy.deepcopy(original)

    copia_rasa["lista"].append(4)
    copia_profunda["aninhado"]["b"] = 2

    class ComCopia:
        def __init__(self, valor):
            self.valor = valor

        def __copy__(self):
            return ComCopia(self.valor)

        def __deepcopy__(self, memo):
            copia = ComCopia(copy.deepcopy(self.valor, memo))
            memo[id(self)] = copia
            return copia


def operacoes_pickle():
    dados = {"nome": "Thales", "lista": [1, 2, 3], "dt": datetime.datetime.now()}

    serializado = pickle.dumps(dados)
    pickle.dumps(dados, protocol=pickle.HIGHEST_PROTOCOL)
    recuperado = pickle.loads(serializado)

    with open("dados.pkl", "wb") as f:
        pickle.dump(dados, f, protocol=pickle.HIGHEST_PROTOCOL)

    with open("dados.pkl", "rb") as f:
        carregado = pickle.load(f)

    class Personalizavel:
        def __getstate__(self):
            estado = self.__dict__.copy()
            del estado["cache"]
            return estado

        def __setstate__(self, estado):
            self.__dict__.update(estado)
            self.cache = {}


# ─────────────────────────────────────────────
# WEAKREF & GC
# ─────────────────────────────────────────────

def operacoes_weakref():
    class Objeto:
        def __init__(self, nome):
            self.nome = nome

    obj = Objeto("teste")
    ref = weakref.ref(obj)
    ref()
    ref() is None

    def callback(r):
        print("objeto destruído")

    ref_com_callback = weakref.ref(obj, callback)
    proxy = weakref.proxy(obj)
    proxy.nome

    wd = weakref.WeakValueDictionary()
    wd["chave"] = obj

    ws = weakref.WeakSet()
    ws.add(obj)

    wkr = weakref.WeakKeyDictionary()
    wkr[obj] = "valor"

    del obj


def operacoes_gc():
    gc.enable()
    gc.disable()
    gc.isenabled()
    gc.collect()
    gc.collect(0)
    gc.collect(1)
    gc.collect(2)
    gc.get_count()
    gc.get_threshold()
    gc.set_threshold(700, 10, 10)
    gc.get_objects()
    gc.get_referrers()
    gc.get_referents()
    gc.is_tracked([])
    gc.is_finalized(object())
    gc.freeze()
    gc.unfreeze()
    gc.get_freeze_count()


# ─────────────────────────────────────────────
# INSPECT
# ─────────────────────────────────────────────

def operacoes_inspect():
    def minha_func(a: int, b: str = "padrão", *args, c: float = 1.0, **kwargs) -> bool:
        pass

    inspect.isfunction(minha_func)
    inspect.isbuiltin(len)
    inspect.ismethod(Animal.fazer_som)
    inspect.isclass(Animal)
    inspect.ismodule(os)
    inspect.isgeneratorfunction(gerador_simples)
    inspect.iscoroutinefunction(funcao_async_simples)
    inspect.isasyncgenfunction(gerador_async)

    inspect.getdoc(Animal)
    inspect.getfile(Animal)
    inspect.getsource(Animal)
    inspect.getsourcelines(Animal)
    inspect.getmodule(Animal)
    inspect.getmembers(Animal)
    inspect.getmembers(Animal, predicate=inspect.isfunction)

    sig = inspect.signature(minha_func)
    sig.parameters
    sig.return_annotation
    sig.bind(1, "olá", c=2.0)
    sig.bind_partial(1)

    for nome, param in sig.parameters.items():
        param.name
        param.default
        param.annotation
        param.kind
        param.kind == inspect.Parameter.POSITIONAL_OR_KEYWORD

    inspect.stack()
    inspect.currentframe()
    inspect.getframeinfo(inspect.currentframe())


# ─────────────────────────────────────────────
# OS & SYS
# ─────────────────────────────────────────────

def operacoes_os():
    os.getcwd()
    os.chdir("/tmp")
    os.listdir(".")
    os.makedirs("a/b/c", exist_ok=True)
    os.removedirs("a/b/c")
    os.rename("origem", "destino") if False else None
    os.remove("arquivo.txt") if False else None
    os.stat(".")
    os.path.join("a", "b", "c")
    os.path.split("/a/b/c.txt")
    os.path.splitext("arquivo.txt")
    os.path.dirname("/a/b/c.txt")
    os.path.basename("/a/b/c.txt")
    os.path.exists("/tmp")
    os.path.isfile("/tmp")
    os.path.isdir("/tmp")
    os.path.abspath(".")
    os.path.realpath(".")
    os.path.expanduser("~")
    os.path.expandvars("$HOME")
    os.path.getsize(".")
    os.path.getatime(".")
    os.path.getmtime(".")
    os.environ.get("PATH", "")
    os.environ.copy()
    os.getenv("HOME")
    os.getpid()
    os.getppid()
    os.cpu_count()
    os.urandom(32)
    os.sep
    os.linesep
    os.pathsep
    os.name
    os.walk(".")

    for raiz, dirs, arquivos in os.walk("."):
        for arquivo in arquivos:
            os.path.join(raiz, arquivo)


def operacoes_sys():
    sys.version
    sys.version_info
    sys.platform
    sys.argv
    sys.path
    sys.modules
    sys.stdin
    sys.stdout
    sys.stderr
    sys.maxsize
    sys.float_info
    sys.int_info
    sys.byteorder
    sys.getdefaultencoding()
    sys.getfilesystemencoding()
    sys.getrecursionlimit()
    sys.setrecursionlimit(10000)
    sys.getsizeof(42)
    sys.getsizeof([])
    sys.getsizeof({})
    sys.exc_info()
    sys.implementation
    sys.prefix
    sys.exec_prefix
    sys.executable
    sys.builtin_module_names
    sys.flags
    sys.hash_info


# ─────────────────────────────────────────────
# STRUCT & BYTES
# ─────────────────────────────────────────────

def operacoes_struct():
    struct.pack("i", 42)
    struct.pack("!I", 12345)
    struct.pack("3sI", b"abc", 100)
    struct.pack(">hhl", 1, 2, 3)

    dados = struct.pack("iif", 1, 2, 3.14)
    struct.unpack("iif", dados)
    struct.unpack_from("i", dados, 0)

    struct.calcsize("iif")
    struct.calcsize("!I")

    s = struct.Struct("!4sHH")
    s.pack(b"abcd", 1, 2)
    s.unpack(s.pack(b"abcd", 1, 2))
    s.size

    dados_bytes = b"\x00\x01\x02\x03"
    int.from_bytes(dados_bytes, byteorder="big")
    int.from_bytes(dados_bytes, byteorder="little")
    (42).to_bytes(4, byteorder="big")
    (42).to_bytes(4, byteorder="little", signed=False)

    bytearray(10)
    ba = bytearray(b"hello")
    ba[0] = 72
    ba.append(33)
    ba.extend(b"!!")
    bytes(ba)
    memoryview(ba)
    mv = memoryview(b"abcdef")
    mv[1:4]
    mv.tobytes()


# ─────────────────────────────────────────────
# STRING AVANÇADO
# ─────────────────────────────────────────────

def formatacao_avancada():
    "{:d}".format(42)
    "{:f}".format(3.14)
    "{:.2f}".format(3.14159)
    "{:e}".format(1234567.89)
    "{:g}".format(0.000123)
    "{:%}".format(0.85)
    "{:,}".format(1000000)
    "{:_}".format(1000000)
    "{:b}".format(42)
    "{:o}".format(42)
    "{:x}".format(42)
    "{:X}".format(42)
    "{:#x}".format(42)
    "{:010d}".format(42)
    "{:<10}".format("esq")
    "{:>10}".format("dir")
    "{:^10}".format("cent")
    "{:*^10}".format("cent")
    "{:+d}".format(42)
    "{: d}".format(42)
    "{0} {1} {0}".format("a", "b")
    "{nome} {sobrenome}".format(nome="Thales", sobrenome="G.")
    "{!r}".format("string")
    "{!s}".format(42)
    "{!a}".format("café")

    f"{42:08b}"
    f"{3.14:.4f}"
    f"{'texto':>20}"
    f"{1000000:_}"
    f"{0.875:.1%}"

    import string
    template = string.Template("Olá $nome, você tem $msgs mensagens")
    template.substitute(nome="Thales", msgs=5)
    template.safe_substitute(nome="Thales")

    textwrap.wrap("texto longo para quebrar em linhas", width=20)
    textwrap.fill("texto longo para preencher", width=20)
    textwrap.dedent("    texto indentado\n    segunda linha")
    textwrap.indent("texto\nsegunda linha", "    ")
    textwrap.shorten("texto muito longo", width=10, placeholder="...")


def operacoes_unicode():
    unicodedata.name("A")
    unicodedata.category("A")
    unicodedata.bidirectional("A")
    unicodedata.combining("A")
    unicodedata.east_asian_width("A")
    unicodedata.mirrored("A")
    unicodedata.decomposition("ñ")
    unicodedata.normalize("NFC", "café")
    unicodedata.normalize("NFD", "café")
    unicodedata.normalize("NFKC", "ﬁ")
    unicodedata.lookup("LATIN SMALL LETTER A")
    unicodedata.unidata_version


# ─────────────────────────────────────────────
# PADRÕES DE PROJETO
# ─────────────────────────────────────────────

class Builder:
    class QueryBuilder:
        def __init__(self, tabela):
            self._tabela = tabela
            self._campos = ["*"]
            self._condicoes = []
            self._ordem = None
            self._limite = None

        def selecionar(self, *campos):
            self._campos = list(campos)
            return self

        def onde(self, condicao):
            self._condicoes.append(condicao)
            return self

        def ordenar(self, campo, desc=False):
            self._ordem = f"{campo} {'DESC' if desc else 'ASC'}"
            return self

        def limitar(self, n):
            self._limite = n
            return self

        def construir(self):
            query = f"SELECT {', '.join(self._campos)} FROM {self._tabela}"
            if self._condicoes:
                query += " WHERE " + " AND ".join(self._condicoes)
            if self._ordem:
                query += f" ORDER BY {self._ordem}"
            if self._limite:
                query += f" LIMIT {self._limite}"
            return query


class Factory:
    class AnimalFactory:
        _classes = {"cachorro": Cachorro, "gato": Gato, "pato": Pato}

        @classmethod
        def criar(cls, tipo, *args, **kwargs):
            classe = cls._classes.get(tipo.lower())
            if not classe:
                raise ValueError(f"tipo desconhecido: {tipo}")
            return classe(*args, **kwargs)

        @classmethod
        def registrar(cls, tipo, classe):
            cls._classes[tipo.lower()] = classe


class Estrategia(ABC):
    @abstractmethod
    def executar(self, dados):
        pass


class EstrategiaOrdenacao(Estrategia):
    def executar(self, dados):
        return sorted(dados)


class EstrategiaOrdenacaoReversa(Estrategia):
    def executar(self, dados):
        return sorted(dados, reverse=True)


class Contexto:
    def __init__(self, estrategia: Estrategia):
        self._estrategia = estrategia

    def definir_estrategia(self, estrategia: Estrategia):
        self._estrategia = estrategia

    def executar(self, dados):
        return self._estrategia.executar(dados)


class Comando(ABC):
    @abstractmethod
    def executar(self):
        pass

    @abstractmethod
    def desfazer(self):
        pass


class GerenciadorComandos:
    def __init__(self):
        self._historico: List[Comando] = []
        self._idx = -1

    def executar(self, cmd: Comando):
        self._historico = self._historico[:self._idx + 1]
        cmd.executar()
        self._historico.append(cmd)
        self._idx += 1

    def desfazer(self):
        if self._idx >= 0:
            self._historico[self._idx].desfazer()
            self._idx -= 1

    def refazer(self):
        if self._idx < len(self._historico) - 1:
            self._idx += 1
            self._historico[self._idx].executar()


class Decorador(ABC):
    def __init__(self, componente):
        self._componente = componente

    @abstractmethod
    def operacao(self):
        pass


class ComponenteBase:
    def operacao(self):
        return "base"


class DecoradorConcreto(Decorador):
    def operacao(self):
        return f"decorado({self._componente.operacao()})"


if __name__ == "__main__":
    print(f"Python {sys.version}")
    print("Arquivo de referência carregado com sucesso.")
    unittest.main(argv=[""], exit=False, verbosity=0)