public class Point3d {
    private double xCoord;
    private double yCoord;
    private double zCoord;

    // Конструктор с параметрами
    public Point3d(double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }

    // Конструктор по умолчанию (0,0,0)
    public Point3d() {
        this(0.0, 0.0, 0.0);
    }

    // Методы доступа
    public double getX() { return xCoord; }
    public double getY() { return yCoord; }
    public double getZ() { return zCoord; }

    // Методы изменения координат
    public void setX(double val) { xCoord = val; }
    public void setY(double val) { yCoord = val; }
    public void setZ(double val) { zCoord = val; }

    // Метод для проверки равенства двух точек
    public boolean equals(Point3d other) {
        return this.xCoord == other.xCoord && this.yCoord == other.yCoord && this.zCoord == other.zCoord;
    }

    // Метод вычисления расстояния между двумя точками
    public double distanceTo(Point3d other) {
        return Math.sqrt(Math.pow(getX() - other.getX(), 2) +
                         Math.pow(getY() - other.getY(), 2) +
                         Math.pow(this.zCoord - other.zCoord, 2));
                        }
                    }