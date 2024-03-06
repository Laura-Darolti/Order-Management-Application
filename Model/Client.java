package org.example.Model;
/**
 * The Client class represents a client entity.
 */
public class Client {

    private String name;
    private Integer id;
    private String address;
    private Integer age;
    private String Email;
    /**
     * Returns the ID of the client.
     *
     * @return the ID of the client
     */
    public int getId() {
        return id;
    }
    /**
     * Returns the email of the client.
     *
     * @return the email of the client
     */
    public String getEmail() {
        return Email;
    }
    /**
     * Sets the email of the client.
     *
     * @param email the email to set for the client
     */
    public void setEmail(String email) {
        Email = email;
    }
    /**
     * Sets the ID of the client.
     *
     * @param id the ID to set for the client
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Returns the name of the client.
     *
     * @return the name of the client
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the client.
     *
     * @param name the name to set for the client
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns the address of the client.
     *
     * @return the address of the client
     */
    public String getAddress() {
        return address;
    }
    /**
     * Sets the address of the client.
     *
     * @param address the address to set for the client
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Returns the age of the client.
     *
     * @return the age of the client
     */
    public int getAge() {
        return age;
    }
    /**
     * Sets the age of the client.
     *
     * @param age the age to set for the client
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Constructs a new Client object with the specified properties.
     *
     * @param name    the name of the client
     * @param address the address of the client
     * @param email   the email of the client
     * @param id      the ID of the client
     * @param age     the age of the client
     */
    public Client(String name, String address, String email, int id, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.Email=email;
        this.age = age;
    }


}
