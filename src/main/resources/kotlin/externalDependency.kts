import com.google.common.base.Optional
import org.iperlon.domain.Person

fun getPersonMaybe(age: Int): Optional<Person> {
    val person = Person()
    person.age = age
    person.isAdult = person.age > 17
    return Optional.of(person)
}
