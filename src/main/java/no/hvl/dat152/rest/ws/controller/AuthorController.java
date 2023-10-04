/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.service.AuthorService;

/**
 * 
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class AuthorController {

	// TODO

	@Autowired
	private AuthorService authorservice;

	@GetMapping("/authors")
	public ResponseEntity<Object> getAllAuthors() {

		List<Author> authors = authorservice.findAllAuthors();
		if (authors != null) {

			return new ResponseEntity<>(authors, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/authors/{id}")
	public ResponseEntity<Object> getAuthorbyID(@PathVariable("id") long id) throws AuthorNotFoundException {

		Author author = authorservice.findById(id);
		if (author != null) {
			return new ResponseEntity<>(author, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping("/authors")
	public ResponseEntity<Object> createAuthor(@RequestBody Author author) {

		Author a1 = authorservice.saveAuthor(author);

		return new ResponseEntity<>(a1, HttpStatus.OK);
	}

	@PutMapping("/authors/{id}")
	public ResponseEntity<Object> updateAuthor(@PathVariable("id") long id, @RequestBody Author author)
			throws AuthorNotFoundException {

		Author authorToUpdate = authorservice.findById(id);
		if (author.getFirstname() != null || author.getFirstname() != "") {
			authorToUpdate.setFirstname(author.getFirstname());
		}

		if (author.getLastname() != null || author.getLastname() != "") {
			authorToUpdate.setLastname(author.getLastname());
		}

		if (author.getBooks() != null) {
			authorToUpdate.setBooks(author.getBooks());
		}

		authorservice.saveAuthor(authorToUpdate);

		return new ResponseEntity<>(authorToUpdate, HttpStatus.OK);
	}

}
