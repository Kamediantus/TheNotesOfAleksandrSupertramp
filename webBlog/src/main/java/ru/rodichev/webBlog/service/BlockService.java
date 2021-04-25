package ru.rodichev.webBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rodichev.webBlog.entity.BlockOfSite;
import ru.rodichev.webBlog.repo.BlockRepository;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    public BlockOfSite getBlockById(Long id){
        return blockRepository.getBlockById(id);
    }

    public String getTextByName(String name){
        return  blockRepository.getTextByName(name);
    }

    public Iterable<BlockOfSite> findAll(){
        return blockRepository.findAll();
    }
    public void saveBlock(BlockOfSite block){
        blockRepository.save(block);
    }
}
