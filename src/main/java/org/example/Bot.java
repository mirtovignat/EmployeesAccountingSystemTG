package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bot extends TelegramLongPollingBot {

    private boolean isRegistration;
    private boolean isLogIn;
    private boolean isAddTheEmployeeToTheDBMS;
    private boolean isDeleteTheEmployeeFromTheDBMS;

    private Employee hrWorker;
    private Employee employee;

    private boolean passwordScan;
    private boolean isSuccessfulAuthorization;
    private boolean isSalaryIssuance;
    private boolean name;
    private boolean check;
    private boolean patronymic;
    private boolean age;
    private boolean phoneNumber;

    private boolean workLogIn = false;

    private boolean workPassword;


    private boolean salaryIssuance;

    EmployeeDAO employeeDAO = new EmployeeDAO();
    DepartmentDAO departmentDAO = new DepartmentDAO();

    public Bot() throws SQLException {
    }


    @Override
    public String getBotUsername() {
        return "Employees_Accounting_System_bot";
    }

    @Override
    public String getBotToken() {
        return "7723899022:AAGYABSwriYIivLIo6W5Lmp3GGMX6mll9F0";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start":

                    sendMessage(chatId, "Добро пожаловать! Данный бот предназначен для кадровиков.\n" +
                            "Если вы хотите устроиться кадровиком на работу, Вы по адресу!\n" +
                            "/register - зарегистрироваться\n" +
                            "/logIn - войти");
                    break;
                case "/register":
                    isRegistration = true;

                    sendMessage(chatId, "Приступим к собеседованию! Ответьте на вопрос:\n" +
                            "Сколько будем дважды два?");
                    break;
                case "/logIn":
                    isLogIn = true;
                    sendMessage(chatId, "Введите ваш логин");
                    break;
                case "/addTheEmployeeToTheDBMS":
                    if (isSuccessfulAuthorization) isAddTheEmployeeToTheDBMS = true;
                    else sendMessage(chatId, "Вы еще не авторизовались!");
                    break;
                case "/salaryIssuance":
                    if (isSuccessfulAuthorization) isSalaryIssuance = true;
                    else sendMessage(chatId, "Вы еще не авторизовались!");
                    break;
                case "/deleteTheEmployeeFromTheDBMS":
                    if (isSuccessfulAuthorization) isDeleteTheEmployeeFromTheDBMS = true;
                    else sendMessage(chatId, "Вы еще не авторизовались!");
                    break;
                default:
                    if (isRegistration) {

                        if (Double.parseDouble(messageText) == 4) {
                            hrWorker = new Employee();

                            check = true;
                        } else {
                            sendMessage(chatId, "Мы Вам обязательно перезвоним!");
                        }
                        if (check) {
                            hrWorker.setDepartmentId(2);
                            hrWorker.setSalary(40000);
                            if (name) {
                                sendMessage(chatId, "Введите имя");
                                hrWorker.setName(messageText);
                                name = false;
                                patronymic = true;
                            } else if (patronymic) {
                                sendMessage(chatId, "Введите отчество");
                                hrWorker.setName(messageText);
                                patronymic = false;
                                age = true;
                            } else if (age) {
                                sendMessage(chatId, "Введите возраст");
                                hrWorker.setName(messageText);
                                age = false;
                                phoneNumber = true;
                            } else if (phoneNumber) {
                                sendMessage(chatId, "Введите номер телефона");
                                hrWorker.setName(messageText);
                                phoneNumber = false;
                                workLogIn = true;
                            } else if (workLogIn) {
                                sendMessage(chatId, "Введите логин");
                                hrWorker.setName(messageText);
                                workLogIn = false;
                                workPassword = true;
                            } else if (workPassword) {
                                sendMessage(chatId, "Введите пароль");
                                hrWorker.setName(messageText);
                            } else {
                                sendMessage(chatId, "Введите фамилию");
                                hrWorker.setSurname(messageText);
                                name = true;
                            }
                        }
                    } else if (isLogIn) {
                        if (!passwordScan) {
                            if (!workLogInExists(messageText)) {
                                sendMessage(chatId, "Пользователь с таким логином не зарегистрирован");
                            } else {
                                sendMessage(chatId, "Введите пароль");
                                passwordScan = true;
                            }
                        } else {
                            sendMessage(chatId, "Введите свой пароль");
                            if (!workPasswordExists(messageText)) {
                                sendMessage(chatId, "Пользователь с таким паролем не зарегистрирован");
                            } else {
                                sendMessage(chatId, "Вы успешно авторизовались!");
                                isSuccessfulAuthorization = true;
                            }
                        }

                    }
                    if (isSuccessfulAuthorization) {
                        sendMessage(chatId, "/addTheEmployeeToTheDBMS - команда для добавления сотрудника в СУБД\n" +
                                "/salaryIssuance - команда для выдачи ЗП\n" +
                                "/deleteTheEmployeeFromTheDBMS - команда для удаления сотрудника из СУБД");
                        if (isAddTheEmployeeToTheDBMS) {
                            employee = new Employee();
                            sendMessage(chatId, "Введите фамилию сотрудника");
                            if (Objects.equals(employee.getSurname(), null)) {
                                employee.setSurname(messageText);
                                sendMessage(chatId, "Введите имя сотрудника");
                            } else if (Objects.equals(employee.getName(), null)) {
                                employee.setName(messageText);
                                sendMessage(chatId, "Введите отчество сотрудника");
                            } else if (Objects.equals(employee.getPatronymic(), null)) {
                                employee.setPatronymic(messageText);
                                sendMessage(chatId, "Введите возраст сотрудника");
                            } else if (Objects.equals(employee.getAge(), 0)) {
                                employee.setPhoneNumber(Long.parseLong(messageText));
                                employee.setAge(Integer.parseInt(messageText));
                                sendMessage(chatId, "Введите контактный номер телефона сотрудника");
                            } else if (Objects.equals(employee.getPhoneNumber(), 0)) {
                                employee.setPhoneNumber(Long.parseLong(messageText));
                                sendMessage(chatId, "Введите предполагаемую зарплату сотрудника");
                            } else if (Objects.equals(employee.getSalary(), 0)) {
                                employee.setSalary(Double.parseDouble(messageText));
                                sendMessage(chatId, "Введите ID департамента, в которой сотрудник устраивается");

                            } else if (Objects.equals(employee.getDepartmentId(), 0)) {
                                employee.setDepartmentId(Integer.parseInt(messageText));

                            }
                            employeeDAO.create(employee);

                        } else if (isSalaryIssuance) {
                            sendMessage(chatId, "Кому хотите выдать ЗП? Введите ID пользователя");

//                            employeeDAO.(employeeDAO.findEntityById(Integer.valueOf(messageText)));
                        } else if (isDeleteTheEmployeeFromTheDBMS) {
                            sendMessage(chatId, "Введите ID пользователя, которого хотите удалить");
                            employeeDAO.delete(employeeDAO.findEntityById(Integer.valueOf(messageText)));
                        }
                    }
            }
        }
    }


    private boolean workLogInExists(String messageText) {
        int i = 0;
        boolean j = true;
        while (!Objects.equals(employeeDAO.findEntityById(i).getWorkLogIn(), messageText)) {
            j = false;
        }
        return j;
    }

    private boolean workPasswordExists(String messageText) {
        int i = 0;
        boolean j = true;
        while (!Objects.equals(employeeDAO.findEntityById(i).getWorkPassword(), messageText)) {
            j = false;
        }
        return j;
    }

    private boolean phoneNumberExists(String messageText) {
        int i = 0;
        boolean j = true;
        while (!Objects.equals(employeeDAO.findEntityById(i).getPhoneNumber(), Long.parseLong(messageText))) {
            j = false;
        }
        return j;
    }


    private ReplyKeyboardMarkup setButtons() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);


        return replyKeyboardMarkup;
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();

//        message.setReplyMarkup(setButtons());

        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }


}
