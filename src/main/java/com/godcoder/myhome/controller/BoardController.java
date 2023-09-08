package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.Type;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.repository.TypeRepository;
import com.godcoder.myhome.service.BoardService;
import com.godcoder.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText
//                       @RequestParam(required = false) Long types
    ) {
//        Page<Board> boards = boardRepository.findAll(pageable);
        Long boardtype = null;
//        if(types == null){
//            boardtype = 4L;
//        }else {
//            boardtype = Long.parseLong(String.valueOf(types));
//        }

        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText ,pageable);
//        Page<Board> boards = boardRepository.findByTitleContainingOrContentContainingOrBoardTypeContaining(searchText, searchText ,pageable, boardtype);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() + 4);
        List<Type> types = typeRepository.findAll();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        model.addAttribute("types", types);
        return  "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id,
                       @RequestParam(required = false) Long types) {
        if(id == null){
            model.addAttribute("board", new Board());
        }else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String postform(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        boardValidator.validate(board, bindingResult);
        //BindingResult가 @Size와 @NotNull 등의 어노테이션이 지정한 조건에 맞지 않으면 
        //bindingResult.hasErrors에 false값을 보내준다
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        String username = authentication.getName();
        boardService.save(username, board);
        return "redirect:/board/list";
    }
}
