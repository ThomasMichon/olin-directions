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
    
    public static Token getAccessToken(int id){
        return getAccessToken(id,null);
        }
    
    public static Token getAccessToken(int id, String nickname){
        if(tokens.containsKey(id)){
            Token t = tokens.get(id);
            if(t==null || nickname==t.getNickname() || nickname==null || t.getNickname().equals(nickname) ){ // nickname matches, or user specified null nickname (don't care what it is)
                //nickname match
                return tokens.get(id);
                }
            //otherwise, we have a nickname mismatch, throw exception
            throw new IllegalArgumentException("Correct ID, wrong nickname");
            }
        if(nickname==null){ nickname="AccessToken"+id; } //default nickname
        Token t = new Token(id,nickname);
        tokens.put(id,t);
        return t;
        }
    
    public static boolean createAccessToken(int id){
        return createAccessToken(id,null);
        }
    
    public static boolean createAccessToken(int id, String nickname){
        return getAccessToken(id,nickname)!=null;
        }
    
    public static void deleteAllTokens(){
        tokens.clear();
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
