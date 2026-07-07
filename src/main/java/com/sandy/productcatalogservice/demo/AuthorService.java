package com.sandy.productcatalogservice.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public String getAuthorDetails() {

        List<Author> authorList = authorRepository.findAllWithBook();

        for(Author author : authorList) {
            System.out.println("No of books by the author  " + author.getName() + " is " + author.getBooks().size());
        }
        return "Author  details Printed";
    }
}
