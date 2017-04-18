JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Node.java \
	binaryHeap.java \
	BinaryNode.java \
	encoder.java \
	decoder.java \
	fileHandling.java \
	fourWayHeap.java \
	huffmanCodeGeneration.java \
	pairingHeapNode.java \
	PairingHeap.java

default: classes

classes: $(CLASSES:.java=.class)

clean: $(RM) *.class
