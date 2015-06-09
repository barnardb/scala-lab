package lab

// We write the main code more or less as we'd like to have it in Scala 2.11,
// and then use these little adapters to get the code compiling in Scala 2.10
package object macros {

  object blackbox {
    type Context = scala.reflect.macros.Context
  }

}
