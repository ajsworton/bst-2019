package com.ajsworton

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}
import org.scalacheck.Gen

class TreeTest extends WordSpec with Matchers with PropertyChecks with TypeCheckedTripleEquals {

  "setValue" should {
    "correctly set the value" in {
      val node = Node(0, Leaf, Leaf)
      node.setValue(5)
      node.value shouldBe 5
    }

    "correctly set the left node" in {
      val node = Node(0, Leaf, Leaf)
      val child = Node(76, Leaf, Leaf)
      node.setLeft(child)
      node.left shouldBe child
    }

    "correctly set the right node" in {
      val node = Node(0, Leaf, Leaf)
      val child = Node(76, Leaf, Leaf)
      node.setRight(child)
      node.right shouldBe child
    }
  }

  "insert" should {
    "correctly place a value to the left of the tree" in {
      val tree = Node(6, Node(4, Leaf, Leaf), Leaf)
      tree.insert(5) shouldBe Node(6, Node(4, Leaf, Node(5, Leaf, Leaf)), Leaf)
    }
    "correctly place a value to the right of the tree" in {
      val tree = Node(6, Node(4, Leaf, Leaf), Leaf)
      tree.insert(9) shouldBe Node(6, Node(4, Leaf, Leaf), Node(9, Leaf, Leaf))
    }
  }

  "preTraverse" should {
    "correctly generate an array of the tree traversal in pre-order" in {
      val tree = Node(7, Node(3, Node(1, Leaf, Node(2, Leaf, Leaf)), Leaf), Node(9, Node(8, Leaf, Leaf), Node(12, Leaf, Leaf)))
      tree.preTraverse shouldBe Array(7,3,1,2,9,8,12)
    }
  }

  "generate" should {

    "return a single node for a single input value" in {
      val arr = Array(49)
      val tree = Tree.generate(arr)
      tree shouldBe Node(49, Leaf, Leaf)
      tree.preTraverse === arr
    }

    "Correctly build a specific BST" in {
      val arr = Array(7,3,1,2,9,8,12)
      val tree = Tree.generate(arr)
      tree.preTraverse === arr
    }

    "correctly build a generic BST" in {
      val sourceArray = for {
        a <- Gen.choose(5001, 10000)
        b <- Gen.choose(0, 5000)
        c <- Gen.choose(10001, 20000)
        d <- Gen.choose(10001, 20000)
      } yield Array(a, b, c, d)

      forAll (sourceArray) { arr =>
        val tree = Tree.generate(arr)
        tree.preTraverse === arr
      }
    }
  }
}
