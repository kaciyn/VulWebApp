package uk.ac.napier.soc.ssd.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import uk.ac.napier.soc.ssd.abac.security.spring.ContextAwarePolicyEnforcement;
import uk.ac.napier.soc.ssd.domain.Blog;
import uk.ac.napier.soc.ssd.repository.BlogRepository;
import uk.ac.napier.soc.ssd.web.rest.errors.BadRequestAlertException;
import uk.ac.napier.soc.ssd.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Blog.
 */
@RestController
@RequestMapping("/api")
public class BlogResource {

    private final Logger log = LoggerFactory.getLogger(BlogResource.class);

    private static final String ENTITY_NAME = "blog";

    private final BlogRepository blogRepository;

    @Autowired
  	private ContextAwarePolicyEnforcement policy;
    
    public BlogResource(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    /**
     * POST  /blogs : Create a new blog.
     *
     * @param blog the blog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blog, or with status 400 (Bad Request) if the blog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blogs")
    @Timed
    public ResponseEntity<Blog> createBlog(@Valid @RequestBody Blog blog) throws URISyntaxException {
        log.debug("REST request to save Blog : {}", blog);
        if (blog.getId() != null) {
            throw new BadRequestAlertException("A new blog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Blog result = blogRepository.save(blog);
        return ResponseEntity.created(new URI("/api/blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blogs : Updates an existing blog.
     *
     * @param blog the blog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blog,
     * or with status 400 (Bad Request) if the blog is not valid,
     * or with status 500 (Internal Server Error) if the blog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blogs")
    @Timed
    public ResponseEntity<Blog> updateBlog(@Valid @RequestBody Blog blog) throws URISyntaxException {
        log.debug("REST request to update Blog : {}", blog);
        if (blog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Blog result = blogRepository.save(blog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blogs : get all the blogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of blogs in body
     */
    @GetMapping("/blogs")
    @Timed
    public List<Blog> getAllBlogs() {
        log.debug("REST request to get all Blogs");
        return blogRepository.findAll();
    }

    /**
     * GET  /blogs/:id : get the "id" blog.
     *
     * @param id the id of the blog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blog, or with status 404 (Not Found)
     */
    @GetMapping("/blogs/{id}")
    @Timed
    public ResponseEntity<Blog> getBlog(@PathVariable Long id) {
        log.debug("REST request to get Blog : {}", id);
        Optional<Blog> blog = blogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(blog);
    }

    /**
     * DELETE  /blogs/:id : delete the "id" blog.
     *
     * @param id the id of the blog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        log.debug("REST request to delete Blog : {}", id);

        blogRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
