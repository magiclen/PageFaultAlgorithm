PageFaultAlgorithm
=================================

# Introduction

**PageFaultAlgorithm** is a Java program which can simulate memory page replacement. It's a very simple implementation, which only has command line interface.

# Usage

## How to run?

You can use the two commands below to run **PageFaultAlgorithm**:

    java -jar PageFaultAlgorithm.jar

or

    java -jar PageFaultAlgorithm.jar ALGORITHM NUMBER_OF_FRAMES REFERENCES...

## Examples

### First in First out Page Replacement

    java -jar PageFaultAlgorithm.jar fifo 3 a b c d a b e a b c d e

The result is,

    Sequence Number: 0
    	Frame 1(victim):
    		Reference: null
    	Frame 2:
    		Reference: null
    	Frame 3:
    		Reference: null
    Sequence Number: 1
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: a
    	Frame 2(victim):
    		Reference: null
    	Frame 3:
    		Reference: null

    Sequence Number: 2
    	Reference: b
    	Page Fault: true
    	Frame 1:
    		Reference: a
    	Frame 2:
    		Reference: b
    	Frame 3(victim):
    		Reference: null

    Sequence Number: 3
    	Reference: c
    	Page Fault: true
    	Frame 1(victim):
    		Reference: a
    	Frame 2:
    		Reference: b
    	Frame 3:
    		Reference: c

    Sequence Number: 4
    	Reference: d
    	Page Fault: true
    	Frame 1:
    		Reference: d
    	Frame 2(victim):
    		Reference: b
    	Frame 3:
    		Reference: c

    Sequence Number: 5
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: d
    	Frame 2:
    		Reference: a
    	Frame 3(victim):
    		Reference: c

    Sequence Number: 6
    	Reference: b
    	Page Fault: true
    	Frame 1(victim):
    		Reference: d
    	Frame 2:
    		Reference: a
    	Frame 3:
    		Reference: b

    Sequence Number: 7
    	Reference: e
    	Page Fault: true
    	Frame 1:
    		Reference: e
    	Frame 2(victim):
    		Reference: a
    	Frame 3:
    		Reference: b

    Sequence Number: 8
    	Reference: a
    	Page Fault: false
    	Frame 1:
    		Reference: e
    	Frame 2(victim):
    		Reference: a
    	Frame 3:
    		Reference: b

    Sequence Number: 9
    	Reference: b
    	Page Fault: false
    	Frame 1:
    		Reference: e
    	Frame 2(victim):
    		Reference: a
    	Frame 3:
    		Reference: b

    Sequence Number: 10
    	Reference: c
    	Page Fault: true
    	Frame 1:
    		Reference: e
    	Frame 2:
    		Reference: c
    	Frame 3(victim):
    		Reference: b

    Sequence Number: 11
    	Reference: d
    	Page Fault: true
    	Frame 1(victim):
    		Reference: e
    	Frame 2:
    		Reference: c
    	Frame 3:
    		Reference: d

    Sequence Number: 12
    	Reference: e
    	Page Fault: false
    	Frame 1(victim):
    		Reference: e
    	Frame 2:
    		Reference: c
    	Frame 3:
    		Reference: d

    Number of Page Fault: 9

### The Clock Page Replacement (Second Chance)

    java -jar PageFaultAlgorithm.jar sc 3 a b c d a b e a b c d e

The result is,

    Sequence Number: 0
    	Frame 1(victim):
    		Reference: null
    		Use Bit: 0
    	Frame 2:
    		Reference: null
    		Use Bit: 0
    	Frame 3:
    		Reference: null
    		Use Bit: 0
    Sequence Number: 1
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: a
    		Use Bit: 1
    	Frame 2(victim):
    		Reference: null
    		Use Bit: 0
    	Frame 3:
    		Reference: null
    		Use Bit: 0

    Sequence Number: 2
    	Reference: b
    	Page Fault: true
    	Frame 1:
    		Reference: a
    		Use Bit: 1
    	Frame 2:
    		Reference: b
    		Use Bit: 1
    	Frame 3(victim):
    		Reference: null
    		Use Bit: 0

    Sequence Number: 3
    	Reference: c
    	Page Fault: true
    	Frame 1(victim):
    		Reference: a
    		Use Bit: 1
    	Frame 2:
    		Reference: b
    		Use Bit: 1
    	Frame 3:
    		Reference: c
    		Use Bit: 1

    Sequence Number: 4
    	Reference: d
    	Page Fault: true
    	Frame 1:
    		Reference: d
    		Use Bit: 1
    	Frame 2(victim):
    		Reference: b
    		Use Bit: 0
    	Frame 3:
    		Reference: c
    		Use Bit: 0

    Sequence Number: 5
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: d
    		Use Bit: 1
    	Frame 2:
    		Reference: a
    		Use Bit: 1
    	Frame 3(victim):
    		Reference: c
    		Use Bit: 0

    Sequence Number: 6
    	Reference: b
    	Page Fault: true
    	Frame 1(victim):
    		Reference: d
    		Use Bit: 1
    	Frame 2:
    		Reference: a
    		Use Bit: 1
    	Frame 3:
    		Reference: b
    		Use Bit: 1

    Sequence Number: 7
    	Reference: e
    	Page Fault: true
    	Frame 1:
    		Reference: e
    		Use Bit: 1
    	Frame 2(victim):
    		Reference: a
    		Use Bit: 0
    	Frame 3:
    		Reference: b
    		Use Bit: 0

    Sequence Number: 8
    	Reference: a
    	Page Fault: false
    	Frame 1:
    		Reference: e
    		Use Bit: 1
    	Frame 2(victim):
    		Reference: a
    		Use Bit: 1
    	Frame 3:
    		Reference: b
    		Use Bit: 0

    Sequence Number: 9
    	Reference: b
    	Page Fault: false
    	Frame 1:
    		Reference: e
    		Use Bit: 1
    	Frame 2(victim):
    		Reference: a
    		Use Bit: 1
    	Frame 3:
    		Reference: b
    		Use Bit: 1

    Sequence Number: 10
    	Reference: c
    	Page Fault: true
    	Frame 1:
    		Reference: e
    		Use Bit: 0
    	Frame 2:
    		Reference: c
    		Use Bit: 1
    	Frame 3(victim):
    		Reference: b
    		Use Bit: 0

    Sequence Number: 11
    	Reference: d
    	Page Fault: true
    	Frame 1(victim):
    		Reference: e
    		Use Bit: 0
    	Frame 2:
    		Reference: c
    		Use Bit: 1
    	Frame 3:
    		Reference: d
    		Use Bit: 1

    Sequence Number: 12
    	Reference: e
    	Page Fault: false
    	Frame 1(victim):
    		Reference: e
    		Use Bit: 1
    	Frame 2:
    		Reference: c
    		Use Bit: 1
    	Frame 3:
    		Reference: d
    		Use Bit: 1

    Number of Page Fault: 9

### Least Recently Used Page Replacement

    java -jar PageFaultAlgorithm.jar lru 3 a b c d a b e a b c d e

The result is,

    Sequence Number: 0
    	Frame 1(least recently used):
    		Reference: null
    		Recent Order: 0
    	Frame 2:
    		Reference: null
    		Recent Order: 1
    	Frame 3:
    		Reference: null
    		Recent Order: 2
    Sequence Number: 1
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: a
    		Recent Order: 2
    	Frame 2(least recently used):
    		Reference: null
    		Recent Order: 0
    	Frame 3:
    		Reference: null
    		Recent Order: 1

    Sequence Number: 2
    	Reference: b
    	Page Fault: true
    	Frame 1:
    		Reference: a
    		Recent Order: 1
    	Frame 2:
    		Reference: b
    		Recent Order: 2
    	Frame 3(least recently used):
    		Reference: null
    		Recent Order: 0

    Sequence Number: 3
    	Reference: c
    	Page Fault: true
    	Frame 1(least recently used):
    		Reference: a
    		Recent Order: 0
    	Frame 2:
    		Reference: b
    		Recent Order: 1
    	Frame 3:
    		Reference: c
    		Recent Order: 2

    Sequence Number: 4
    	Reference: d
    	Page Fault: true
    	Frame 1:
    		Reference: d
    		Recent Order: 2
    	Frame 2(least recently used):
    		Reference: b
    		Recent Order: 0
    	Frame 3:
    		Reference: c
    		Recent Order: 1

    Sequence Number: 5
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: d
    		Recent Order: 1
    	Frame 2:
    		Reference: a
    		Recent Order: 2
    	Frame 3(least recently used):
    		Reference: c
    		Recent Order: 0

    Sequence Number: 6
    	Reference: b
    	Page Fault: true
    	Frame 1(least recently used):
    		Reference: d
    		Recent Order: 0
    	Frame 2:
    		Reference: a
    		Recent Order: 1
    	Frame 3:
    		Reference: b
    		Recent Order: 2

    Sequence Number: 7
    	Reference: e
    	Page Fault: true
    	Frame 1:
    		Reference: e
    		Recent Order: 2
    	Frame 2(least recently used):
    		Reference: a
    		Recent Order: 0
    	Frame 3:
    		Reference: b
    		Recent Order: 1

    Sequence Number: 8
    	Reference: a
    	Page Fault: false
    	Frame 1:
    		Reference: e
    		Recent Order: 1
    	Frame 2:
    		Reference: a
    		Recent Order: 2
    	Frame 3(least recently used):
    		Reference: b
    		Recent Order: 0

    Sequence Number: 9
    	Reference: b
    	Page Fault: false
    	Frame 1(least recently used):
    		Reference: e
    		Recent Order: 0
    	Frame 2:
    		Reference: a
    		Recent Order: 1
    	Frame 3:
    		Reference: b
    		Recent Order: 2

    Sequence Number: 10
    	Reference: c
    	Page Fault: true
    	Frame 1:
    		Reference: c
    		Recent Order: 2
    	Frame 2(least recently used):
    		Reference: a
    		Recent Order: 0
    	Frame 3:
    		Reference: b
    		Recent Order: 1

    Sequence Number: 11
    	Reference: d
    	Page Fault: true
    	Frame 1:
    		Reference: c
    		Recent Order: 1
    	Frame 2:
    		Reference: d
    		Recent Order: 2
    	Frame 3(least recently used):
    		Reference: b
    		Recent Order: 0

    Sequence Number: 12
    	Reference: e
    	Page Fault: true
    	Frame 1(least recently used):
    		Reference: c
    		Recent Order: 0
    	Frame 2:
    		Reference: d
    		Recent Order: 1
    	Frame 3:
    		Reference: e
    		Recent Order: 2

    Number of Page Fault: 10

### Optimal Page Replacement

    java -jar PageFaultAlgorithm.jar optimal 3 a b c d a b e a b c d e

The result is,

    Sequence Number: 0
    	Frame 1(victim):
    		Reference: null
    		Next Reference Sequence: Undefined
    	Frame 2:
    		Reference: null
    		Next Reference Sequence: Undefined
    	Frame 3:
    		Reference: null
    		Next Reference Sequence: Undefined
    Sequence Number: 1
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: a
    		Next Reference Sequence: 5
    	Frame 2(victim):
    		Reference: null
    		Next Reference Sequence: Undefined
    	Frame 3:
    		Reference: null
    		Next Reference Sequence: Undefined

    Sequence Number: 2
    	Reference: b
    	Page Fault: true
    	Frame 1:
    		Reference: a
    		Next Reference Sequence: 5
    	Frame 2:
    		Reference: b
    		Next Reference Sequence: 6
    	Frame 3(victim):
    		Reference: null
    		Next Reference Sequence: Undefined

    Sequence Number: 3
    	Reference: c
    	Page Fault: true
    	Frame 1:
    		Reference: a
    		Next Reference Sequence: 5
    	Frame 2:
    		Reference: b
    		Next Reference Sequence: 6
    	Frame 3(victim):
    		Reference: c
    		Next Reference Sequence: 10

    Sequence Number: 4
    	Reference: d
    	Page Fault: true
    	Frame 1:
    		Reference: a
    		Next Reference Sequence: 5
    	Frame 2:
    		Reference: b
    		Next Reference Sequence: 6
    	Frame 3(victim):
    		Reference: d
    		Next Reference Sequence: 11

    Sequence Number: 5
    	Reference: a
    	Page Fault: false
    	Frame 1:
    		Reference: a
    		Next Reference Sequence: 8
    	Frame 2:
    		Reference: b
    		Next Reference Sequence: 6
    	Frame 3(victim):
    		Reference: d
    		Next Reference Sequence: 11

    Sequence Number: 6
    	Reference: b
    	Page Fault: false
    	Frame 1:
    		Reference: a
    		Next Reference Sequence: 8
    	Frame 2:
    		Reference: b
    		Next Reference Sequence: 9
    	Frame 3(victim):
    		Reference: d
    		Next Reference Sequence: 11

    Sequence Number: 7
    	Reference: e
    	Page Fault: true
    	Frame 1:
    		Reference: a
    		Next Reference Sequence: 8
    	Frame 2:
    		Reference: b
    		Next Reference Sequence: 9
    	Frame 3(victim):
    		Reference: e
    		Next Reference Sequence: 12

    Sequence Number: 8
    	Reference: a
    	Page Fault: false
    	Frame 1(victim):
    		Reference: a
    		Next Reference Sequence: No
    	Frame 2:
    		Reference: b
    		Next Reference Sequence: 9
    	Frame 3:
    		Reference: e
    		Next Reference Sequence: 12

    Sequence Number: 9
    	Reference: b
    	Page Fault: false
    	Frame 1(victim):
    		Reference: a
    		Next Reference Sequence: No
    	Frame 2:
    		Reference: b
    		Next Reference Sequence: No
    	Frame 3:
    		Reference: e
    		Next Reference Sequence: 12

    Sequence Number: 10
    	Reference: c
    	Page Fault: true
    	Frame 1:
    		Reference: c
    		Next Reference Sequence: No
    	Frame 2(victim):
    		Reference: b
    		Next Reference Sequence: No
    	Frame 3:
    		Reference: e
    		Next Reference Sequence: 12

    Sequence Number: 11
    	Reference: d
    	Page Fault: true
    	Frame 1(victim):
    		Reference: c
    		Next Reference Sequence: No
    	Frame 2:
    		Reference: d
    		Next Reference Sequence: No
    	Frame 3:
    		Reference: e
    		Next Reference Sequence: 12

    Sequence Number: 12
    	Reference: e
    	Page Fault: false
    	Frame 1(victim):
    		Reference: c
    		Next Reference Sequence: No
    	Frame 2:
    		Reference: d
    		Next Reference Sequence: No
    	Frame 3:
    		Reference: e
    		Next Reference Sequence: No

    Number of Page Fault: 7

### Random Page Replacement

    java -jar PageFaultAlgorithm.jar random 3 a b c d a b e a b c d e

The result is,

    Sequence Number: 0
    	Frame 1(victim):
    		Reference: null
    	Frame 2:
    		Reference: null
    	Frame 3:
    		Reference: null
    Sequence Number: 1
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: a
    	Frame 2(victim):
    		Reference: null
    	Frame 3:
    		Reference: null

    Sequence Number: 2
    	Reference: b
    	Page Fault: true
    	Frame 1:
    		Reference: a
    	Frame 2:
    		Reference: b
    	Frame 3(victim):
    		Reference: null

    Sequence Number: 3
    	Reference: c
    	Page Fault: true
    	Frame 1(victim):
    		Reference: a
    	Frame 2:
    		Reference: b
    	Frame 3:
    		Reference: c

    Sequence Number: 4
    	Reference: d
    	Page Fault: true
    	Frame 1:
    		Reference: d
    	Frame 2(victim):
    		Reference: b
    	Frame 3:
    		Reference: c

    Sequence Number: 5
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: d
    	Frame 2(victim):
    		Reference: a
    	Frame 3:
    		Reference: c

    Sequence Number: 6
    	Reference: b
    	Page Fault: true
    	Frame 1:
    		Reference: d
    	Frame 2:
    		Reference: b
    	Frame 3(victim):
    		Reference: c

    Sequence Number: 7
    	Reference: e
    	Page Fault: true
    	Frame 1:
    		Reference: d
    	Frame 2(victim):
    		Reference: b
    	Frame 3:
    		Reference: e

    Sequence Number: 8
    	Reference: a
    	Page Fault: true
    	Frame 1:
    		Reference: d
    	Frame 2(victim):
    		Reference: a
    	Frame 3:
    		Reference: e

    Sequence Number: 9
    	Reference: b
    	Page Fault: true
    	Frame 1:
    		Reference: d
    	Frame 2(victim):
    		Reference: b
    	Frame 3:
    		Reference: e

    Sequence Number: 10
    	Reference: c
    	Page Fault: true
    	Frame 1:
    		Reference: d
    	Frame 2:
    		Reference: c
    	Frame 3(victim):
    		Reference: e

    Sequence Number: 11
    	Reference: d
    	Page Fault: false
    	Frame 1:
    		Reference: d
    	Frame 2:
    		Reference: c
    	Frame 3(victim):
    		Reference: e

    Sequence Number: 12
    	Reference: e
    	Page Fault: false
    	Frame 1:
    		Reference: d
    	Frame 2:
    		Reference: c
    	Frame 3(victim):
    		Reference: e

    Number of Page Fault: 10

# License

    Copyright 2015 magiclen.org

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

# What's More?

Please check out our web page at

http://magiclen.org/page-fault/
