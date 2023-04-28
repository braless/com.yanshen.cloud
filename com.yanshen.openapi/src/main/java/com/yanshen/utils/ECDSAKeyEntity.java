package com.yanshen.utils;

public class ECDSAKeyEntity {


        public String publicKey;//公钥
        public String privateKey;//私钥
        public String getPublicKey() {
            return publicKey;
        }
        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }
        public String getPrivateKey() {
            return privateKey;
        }
        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
        @Override
        public String toString() {
            return "ECDSAEntity [publicKey=" + publicKey + ", privateKey=" + privateKey + "]";
        }



}
