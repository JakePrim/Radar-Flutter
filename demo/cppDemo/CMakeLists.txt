cmake_minimum_required(VERSION 3.19)
project(cppDemo)

set(CMAKE_CXX_STANDARD 14)

add_executable(cppDemo main.cpp ArrayList.cpp ArrayList.h friendFun.cpp friendClass.cpp)