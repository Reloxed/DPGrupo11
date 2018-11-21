package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.HandyWorker;


@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {
	//C/10
	@Query("select h from HandyWorker h where h.applications.size/ (select avg(h1.applications.size) from HandyWorker h1)>=1.1 order by h.applications.size")
	Collection<HandyWorker> findMoreApplicationsThanAvg();
	//B/5
	@Query("select h from HandyWorker h join h.applications a join a.fixUpTask f group by h order by count(f.complaints.size) desc")
	Collection<HandyWorker> findTopComplaintsHandyWorkers();
	
	@Query("select h from HandyWorker h where h.userAccount.id = ?1")
	HandyWorker findByUserAccountId(int userAccountId);
	
	//-C
	//Casos de usos:
	//Un actor que se autentica debe ser capaz de:Editar sus datos personales.
	//Un actor que se autentica como un cliente debe ser capaz de Manejar un número arbitrario de tareas fix-up, que incluye perfil, mostrando, creating, actualización y supresión de ellos.
	//Un actor que se autentica como un cliente debe ser capaz de: 1. Manejar un número arbitrario de tareas fix-up, que incluye perfil, mostrando, creating, actualización y supresión de ellos
	//2. Gestionar las solicitudes de sus tareas fix-up, que incluye la lista y updating ellos
	//Manage his or her applications, which includes listing them, showing them, and creating them. When a handy worker applies for a fix-up task, he or she can set an offered price, and add some comments.
	//When a customer accepts an application, then the corresponding handy worker can create a work plan for the corresponding fix-up task. They can fully manage the work plan, which includes showing them, creating, updating, and deleting phases.
	//req no funcionales:nada
	//-B
	//R.I:Handy workers have a finder in which they can specify some filters:The finder stores the fix-up tasks that pass the filters for one hour by default.
	//casos de usos:
	//37. An actor who is authenticated as a handy worker must be able to:
	//1. Change the filters of his or her finder.
/*	2. Display the fix-up tasks in his or her finder.
	3. List and show the complaints regarding the fix-up tasks in which he or she’s been involved.
	4. Write a note regarding any of the reports that a referee‘s written regarding any of
	the complaints in which he or she’s involved.
	5. Write a comment in a note regarding any of the reports that a referee’s written on
	any of the complaints in which he or she’s involved.*/
	//-A
	/*
	 * 49. An actor who is authenticated as a handy worker must be able to:
	 * 2. Manage his or her endorsements, which includes listing them, showing them, creating an endorsement about a customer for whom he or she’s worked, updating them,
and deleting them
	 * */
	
	
	
}
