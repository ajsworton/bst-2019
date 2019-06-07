package com.ajsworton

import scala.annotation.tailrec

object Main extends App {

  val arr = Array(7,3,1,2,9,8,12)
  val tree = Tree.generate(arr)

  println(tree)

}

sealed trait Tree {
  def preTraverse: Array[Int] = {

    def preorder(tree: Tree): List[Int] = tree match {
      case Node(value, left, right) => value :: preorder(left) ::: preorder(right)
      case Leaf => Nil
    }

    preorder(this).toArray
  }
}

case object Leaf extends Tree

final case class Node(var value: Int, var left: Tree, var right: Tree) extends Tree {
  def setValue(newValue: Int): Node = {
    this.value = newValue
    this
  }
  def setLeft(child: Tree): Node = {
    this.left = child
    this
  }
  def setRight(child: Tree): Node = {
    this.right = child
    this
  }

  def insert(value: Int): Node = {
    @tailrec
    def findLeaf(currentNode: Node, value: Int): Node = (currentNode, value < currentNode.value) match {
      case (node@Node(_, Leaf, _), true) => node.setLeft(Node(value, Leaf, Leaf))
      case (node@Node(_, _, Leaf), false) => node.setRight(Node(value, Leaf, Leaf))
      case (Node(_, node@Node(_, _, _), _), true) => findLeaf(node, value)
      case (Node(_, _, node@Node(_, _, _)), false) => findLeaf(node, value)
    }

    findLeaf(this, value)
    this
  }
}

object Tree {

  def generate(arr: Array[Int]): Tree = {

    val elements = arr.toList

    @tailrec
    def build(root: Node, slice: List[Int]): Tree = slice match {
      case currentValue :: Nil => root.insert(currentValue)
      case currentValue :: remainingValues =>
        build(root.insert(currentValue), remainingValues)
    }

    elements match {
      case head:: Nil => Node(head, Leaf, Leaf)
      case head :: tail => build(Node(head, Leaf, Leaf), tail)
      case Nil => Leaf //this will not happen from spec, but here for completeness
    }

  }
}
