package lab.macros

import scala.language.{existentials, higherKinds}
import scala.language.experimental.macros
import scala.reflect.macros._

object TypeApplicationInMacroDefinition {

  def typeAsStringImpl[T: c.WeakTypeTag](c: blackbox.Context): c.Expr[String] = {
    import c.universe._
    c.Expr[String](Literal(Constant(weakTypeOf[T].toString)))
  }

  def typeAsStringWithObscureWorkaroundImpl[A: c.WeakTypeTag, T <: C[A] forSome {type C[_]}: c.WeakTypeTag](c: blackbox.Context): c.Expr[String] = {
    import c.universe._
    c.Expr[String](Literal(Constant(weakWrappedTypeOf[A, T](c).toString)))
  }

  def weakWrappedTypeOf[A: c.WeakTypeTag, T <: C[A] forSome {type C[_]}: c.WeakTypeTag](c: blackbox.Context): c.Type = {
    import c.universe._
    appliedType(weakTypeOf[T].typeConstructor, List(weakTypeOf[A]))
  }
}

final abstract class TypeApplicationInMacroDefinition[C[_]] {
  def wrappedTypeAsString[A]: String = macro TypeApplicationInMacroDefinition.typeAsStringImpl[C[A]]
  def wrappedTypeAsStringWithUndesirableSignature[T <: C[_]]: String = macro TypeApplicationInMacroDefinition.typeAsStringImpl[T]
  def wrappedTypeAsStringWithObscureWorkaround[A]: String = macro TypeApplicationInMacroDefinition.typeAsStringWithObscureWorkaroundImpl[A, C[A]]
}
