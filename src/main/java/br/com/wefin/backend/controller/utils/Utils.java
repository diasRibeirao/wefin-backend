package br.com.wefin.backend.controller.utils;

public class Utils {

    public static boolean isCpfValid(String cpf) {
        // Somente números com 11 dígitos
        if (!cpf.matches("(\\d{11})")) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (caso contrário, é um CPF inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstDigit = (sum * 10) % 11;
        if (firstDigit == 10) {
            firstDigit = 0;
        }

        // Verifica o primeiro dígito verificador
        if (Character.getNumericValue(cpf.charAt(9)) != firstDigit) {
            return false;
        }

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondDigit = (sum * 10) % 11;
        if (secondDigit == 10) {
            secondDigit = 0;
        }

        // Verifica o segundo dígito verificador
        if (Character.getNumericValue(cpf.charAt(10)) != secondDigit) {
            return false;
        }

        return true; // CPF válido
    }

    public static boolean isCnpjValid(String cnpj) {
        // Somente números com 14 dígitos
        if (!cnpj.matches("(\\d{14})")) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (caso contrário, é um CNPJ inválido)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        int weight = 2;
        for (int i = 11; i >= 0; i--) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum += digit * weight;
            weight = (weight + 1) % 10 == 0 ? 2 : weight + 1;
        }
        int firstDigit = (sum % 11) < 2 ? 0 : 11 - (sum % 11);

        // Verifica o primeiro dígito verificador
        if (Character.getNumericValue(cnpj.charAt(12)) != firstDigit) {
            return false;
        }

        // Calcula o segundo dígito verificador
        sum = 0;
        weight = 2;
        for (int i = 12; i >= 0; i--) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum += digit * weight;
            weight = (weight + 1) % 10 == 0 ? 2 : weight + 1;
        }
        int secondDigit = (sum % 11) < 2 ? 0 : 11 - (sum % 11);

        // Verifica o segundo dígito verificador
        if (Character.getNumericValue(cnpj.charAt(13)) != secondDigit) {
            return false;
        }

        return true; // CNPJ válido
    }
}
