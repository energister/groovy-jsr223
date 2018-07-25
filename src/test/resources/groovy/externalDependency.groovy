import com.google.common.base.Optional
import org.iperlon.groovy.Person

static Optional<Person> getPersonMaybe(int age) {
    def person = new Person()
    person.age = age
    person.adult = person.age > 17
    Optional.of(person)
}
