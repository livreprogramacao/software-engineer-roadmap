public StubInput implements Input {

    private final String stubValue;

    public Input( String stubValue ) {
        this.stubValue = stubValue;
    }

    public String fetch() {
        return stubValue;
    }

}