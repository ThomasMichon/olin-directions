package edu.olin.maps.graph.access.tokens;

import java.util.HashMap;
import java.util.Map;

/**
 * Wheee!
 */

public class Token {
    
    private int id;
    private String nickname;
    
    private static final Map<Integer, Token> tokens = new HashMap();
    
    public static Token getAccessToken(int id, String nickname){
        if(tokens.containsKey(id)){
            return tokens.get(id);
            }
        Token t = new Token(id,nickname);
        tokens.put(id,t);
        return t;
        }
    
    private Token(int id, String nickname){
        this.id = id;
        this.nickname = nickname;
        }
    
    public int getID(){
        return id;
        }
    
    public String getNickname(){
        return nickname;
        }
    
    public boolean equals(Object other){
        if(other==null || !(other instanceof Token)){
            return false;
            }
        return ((Token) other).id == this.id;
        }
    
    }
