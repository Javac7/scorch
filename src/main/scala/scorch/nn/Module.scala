package scorch.nn

import scorch.autograd.Variable

import scala.language.higherKinds

sealed trait Infer[F[_]]
trait LowPriority {
  implicit def inferDefault[F[_]]: Infer[F] = new Infer[F] {}
}
object Infer extends LowPriority {
  type Id[A] = A
  implicit def inferId: Infer[Id] = new Infer[Id] {}
}

abstract class BaseModule(localParameters: Seq[Variable] = Nil) {

  def subModules: Seq[BaseModule] = Seq.empty

  def parameters: Seq[Variable] =
    localParameters ++ subModules.flatMap(_.parameters)

  def zeroGrad(): Unit =
    parameters.map(_.grad).foreach(g => g.data := 0)

  /*
  Pytorch way of solving distinction between training and test mode is by using a mutable variable.
  Perhaps there is a better way.
   */
  var inTrainingMode: Boolean = false

  /*
  Sets the module in training mode.
  This has any effect only on modules such as Dropout or BatchNorm.
   */
  def train(mode: Boolean = true): Unit = {
    this.inTrainingMode = mode
    subModules.foreach(_.train(mode))
  }

  /*
  Sets the module in evaluation mode.
  This has any effect only on modules such as Dropout or BatchNorm.
   */
  def eval(): Unit = train(false)

}

abstract class Module[F[_]: Infer](localParameters: Seq[Variable] = Nil)
    extends BaseModule(localParameters) {
  def forward(x: F[Variable]): F[Variable]
  def apply(x: F[Variable]): F[Variable] = forward(x)
}
