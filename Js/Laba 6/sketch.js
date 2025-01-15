let sound; // Переменная где будет находится аудио-дорожка
let isInitialised; // Состояние, которое обозначает инициализированы ли значения или нет
let isLoaded = false;
let amplitude;
let amplitudes = [];
let fft; // Библиотека для визуализации музыки
let playButton, rewindButton, forwardButton, nextButton, prevButton;
let progressSlider;
let speedSlider, rangeSlider;
let timeText;

function preload() {
    soundFormats('mp3', 'wav'); // Определяем аудио форматы, поддерживаемые плеером
    sound = loadSound('/assets/Tokyo Machine - HOT SHOT.mp3', () => {
        console.log("sound is loaded!"); // Загружаем музыку и при успешной загрузке выводим в консоль сообщение
        isLoaded = true;
    });
    isInitialised = false;
    sound.setVolume(0.2); // Устанавливаем громкость на 20%
}

function setup() {
    createCanvas(1024, 1024);
    textAlign(CENTER); // Центрируем текст по центру
    textSize(16);
    
    speedSlider = createSlider(0.5, 3, 1, 0.1);
    speedSlider.position(40, 60);
    rangeSlider = createSlider(10, 100, 50, 5); // Уменьшен диапазон размеров звезд
    rangeSlider.position(40, 140);
    
    amplitude = new p5.Amplitude();

    for (let i = 0; i < 512; i++)
        amplitudes.push(0);

    fft = new p5.FFT();
    
    colorMode(HSB, 360, 100, 100)
}

function draw() {
    background(0);
    fill(255);
    noStroke(); // Убираем обводку текста
    text("Скорость", speedSlider.x + speedSlider.width / 2, speedSlider.y - 10);
    text("Размер звезд", rangeSlider.x + rangeSlider.width / 2, rangeSlider.y - 10);
    
    if (isInitialised) {
        sound.rate(speedSlider.value()); // Изменение скорости воспроизведения
    }

    if (isInitialised && !sound.isPlaying()) {
        text("Press any key to play sound", width / 2, height / 2);
    } else if (sound.isPlaying()) {
        let freqs = fft.analyze();
        // Визуализатор на основе частот
        let barWidth = width / (freqs.length / 5);
        stroke(0, 150, 0);
        for (let i = 2; i < freqs.length - 2; i += 5) {
            let avg = (freqs[i - 2] + freqs[i - 1] + freqs[i] + freqs[i + 1] + freqs[i + 2]) / 5;
            let barHeight = map(avg, 0, 255, 0, height / 2);
            fill(map(i, 0, freqs.length, 0, 255), 100, 150);
            rect((i / 5) * barWidth, height - barHeight, barWidth - 2, barHeight);
        }

        noStroke();
        // Отображение волны звука
        drawWave();
        drawStars();
    }
}

function drawWave() {
    let level = amplitude.getLevel();
    amplitudes.push(level);
    amplitudes.shift();

    let waveColor = map(level, 0, 0.2, 180, 360);
    stroke(waveColor, 100, 100);
    strokeWeight(2);
    noFill();

    beginShape();
    for (let i = 0; i < amplitudes.length; i++) {
        let y = map(amplitudes[i], 0, 0.2, height / 2, 0);
        vertex(map(i, 0, amplitudes.length, 0, width), y);
    }
    endShape();
}

function keyPressed() {
    if (!isInitialised) {
        isInitialised = true;

        let r = speedSlider.value(); // Скорость воспроизведения звука
        if (isLoaded)
            sound.loop(0, r); // loop - функция для зацикливания звука
    } else {
        if (key == ' ') {
            if (sound.isPaused()) sound.play();
            else sound.pause();
        }
    }
}

function drawStars() {
    let strokeRange = rangeSlider.value();
    for (let i = 0; i < 10; i++) {
        let colorChoice = random([0, 120, 240]); // RGB цвета: красный, зеленый, синий
        fill(colorChoice, 100, 100, 0.8); // Случайный цвет (RGB)
        let x = random(width); // Координата центра звезды
        let y = random(height); 
        let radius1 = random(strokeRange / 2, strokeRange); // Радиус внешних вершин звезды
        let radius2 = radius1 / 2; // Радиус внутренних вершин звезды

        drawStar(x, y, radius1, radius2, 5); // Рисуем звезду с 5 вершинами
    }
}

function drawStar(x, y, radius1, radius2, npoints) {
    let angle = TWO_PI / npoints;
    let halfAngle = angle / 2.0;
    beginShape();
    for (let a = 0; a < TWO_PI; a += angle) {
        let sx = x + cos(a) * radius1;
        let sy = y + sin(a) * radius1;
        vertex(sx, sy);
        sx = x + cos(a + halfAngle) * radius2;
        sy = y + sin(a + halfAngle) * radius2;
        vertex(sx, sy);
    }
    endShape(CLOSE);
}
