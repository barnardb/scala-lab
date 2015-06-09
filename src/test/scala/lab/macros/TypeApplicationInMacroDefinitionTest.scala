package lab.macros

import org.scalatest.FunSuite

class TypeApplicationInMacroDefinitionTest extends FunSuite {

  class Wrapper[T]
  val wrapperApplier: TypeApplicationInMacroDefinition[Wrapper] = null

  test("STRANGE: when type application happens inside a macro definition, the macro gets a WeakTypeTag with no knowledge of the type arguments") {
    assertResult(s"TypeApplicationInMacroDefinitionTest.this.Wrapper[_]") {
      wrapperApplier.wrappedTypeAsString[Int]
    }
  }

  test("when type application happens at the macro call site, the macro gets a WeakTypeTag with knowledge of the type arguments") {
    assertResult(s"TypeApplicationInMacroDefinitionTest.this.Wrapper[Int]") {
      wrapperApplier.wrappedTypeAsStringWithUndesirableSignature[Wrapper[Int]]
    }
  }

  test("but all the type information we would have expected is available for the macro definition to pass to the macro: we can work around the strangeness by having the macro definition pass the type arguments to the macro individually in addition to passing the applied type, and then have the macro explicitly reapply the type") {
    assertResult(s"TypeApplicationInMacroDefinitionTest.this.Wrapper[Int]") {
      wrapperApplier.wrappedTypeAsStringWithObscureWorkaround[Int]
    }
  }

}
