package service.users

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait UserRepository {
  def getUsers: Future[List[User]]

  def createUser(user: String): Future[User]

  def getUser(id: Int): Future[Option[User]]

}

final class UserRepositoryImpl extends UserRepository {

  private val users = List(
    User("Jack", "Dorsey", "jack@twitter.com"),
    User("Steve", "Jobs", "steve@apple.com")
  )

  def getUsers: Future[List[User]] =
    Future.successful(users)

  def getUser(id: Int): Future[Option[User]] =
    Future.successful(users.lift(id))

  override def createUser(user: String): Future[User] = {
    Future {
      User(user, user, user)
    }
  }
}
