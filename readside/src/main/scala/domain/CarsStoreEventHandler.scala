package domain

import cats.effect.Effect
import cats.{Monad, ~>}
import domain.events._
import cats.implicits._
import utils.functional._

class CarsStoreEventHandler[G[_] : Monad, F[_] : Effect](store: StoreAlgebra[G], elog: EventLogAlgebra[F])
                                                        (implicit trans: G ~> F) {


  def start(): Unit = ???


  def updateStore(ev: Event): F[Unit] = ev match {
    case CarRegistered(plate, model) => store.registerCar(plate, model).as(()).liftTo[F]

    case CarRepaired(plate) => store.repairCar(plate).as(()).liftTo[F]

    case PartMarked(plate, part) =>  store.markPart(plate, part).as(()).liftTo[F]

    case PartUnmarked(plate, part) => store.unmarkPart(plate, part).as(()).liftTo[F]

    case PartRepaired(plate, part) => store.repairPart(plate, part).as(()).liftTo[F]
  }
}

object CarsStoreEventHandler {
  def apply[G[_] : Monad, F[_] : Effect](store: StoreAlgebra[G], elog: EventLogAlgebra[F])
                                        (implicit trans: G ~> F) = new CarsStoreEventHandler(store, elog)
}
