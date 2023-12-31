package uvt.tw.bookish.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uvt.tw.bookish.controllers.requests.BookshelfRequest;
import uvt.tw.bookish.controllers.requests.UserInfoDAO;
import uvt.tw.bookish.entities.*;
import uvt.tw.bookish.repositories.BookRepository;
import uvt.tw.bookish.repositories.BookshelfRepository;
import uvt.tw.bookish.repositories.UserRepository;
import uvt.tw.bookish.repositories.WishlistRepository;
import uvt.tw.bookish.services.ProfileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getUserShelf(int id) {
        return bookshelfRepository.findByOwnerID_Id(id)
                .stream()
                .map(Bookshelf::getBookID)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Bookshelf addBookshelfEntry(BookshelfRequest entry) {

        try {
            Book book = bookRepository.findById(entry.getBookID()).orElseThrow();
            User owner = userRepository.findById(entry.getUserID()).orElseThrow();

            Bookshelf result = Bookshelf.builder()
                    .bookID(book)
                    .ownerID(owner)
                    .build();

            bookshelfRepository.add(entry.getBookID(), entry.getUserID());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean deleteBookshelfEntry(int bookID, int ownerID) {
        Optional<Bookshelf> result = bookshelfRepository.findBookshelfByBookID_IdAndOwnerID_Id(bookID, ownerID);

        if(result.isPresent()) {
            bookshelfRepository.delete(result.get());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<Book> getUserWishlist(int id) {
        return wishlistRepository.findByOwnerID_Id(id)
                .stream()
                .map(Wishlist::getBookID)
                .collect(Collectors.toList());
    }

    @Override
    public Wishlist addWishlistEntry(BookshelfRequest entry) {
        Book book = bookRepository.findById(entry.getBookID()).orElseThrow();
        User owner = userRepository.findById(entry.getUserID()).orElseThrow();

        Wishlist result = Wishlist.builder()
                .bookID(book)
                .ownerID(owner)
                .build();

        return wishlistRepository.save(result);
    }

    @Override
    public boolean deleteWishlistEntry(int bookID, int ownerID) {
        Optional<Wishlist> result = wishlistRepository.findWishlistByBookID_IdAndOwnerID_Id(bookID, ownerID);

        if(result.isPresent()) {
            wishlistRepository.delete(result.get());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public UserInfoDAO getUserInfo(int id) {
        User user = userRepository.findById(id).orElseThrow();
        UserInfoDAO infoDAO = UserInfoDAO.builder()
                .bio(user.getBio())
                .profilePicture(user.getProfilePicture())
                .location(user.getLocation())
                .username(user.getUsername())
                .build();

        return infoDAO;
    }
}
