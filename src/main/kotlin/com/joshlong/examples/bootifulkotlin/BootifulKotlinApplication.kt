package com.joshlong.examples.bootifulkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootifulKotlinApplication(val personRepository: PersonRepository) : CommandLineRunner{

    override fun run(vararg args: String?){

        personRepository.deleteAll()
        Stream.of("Phill, Webb", "Dave, Syer", "Spencer, Gibb", "Brian, Crozel")
                .map{ fn -> fn.split(",")}
                .forEach{ tpl -> personRepository.save(Person(tpl[0], tpl[1]))}

        personRepository.all().forEach{println(it)}
    }
}

fun main(args: Array<String>) {
    runApplication<BootifulKotlinApplication>(*args)
}

interface PersonRepository : MongoRepository <Person, String> {

    @Query({""})
    fun all() : Stream<Person>
}
@Document
data class Person(val firstname: String ? = null,
             val lastName: String ?= null,
             @Id val id: String?=null)