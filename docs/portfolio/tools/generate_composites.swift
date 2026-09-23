import AppKit

let root = URL(fileURLWithPath: CommandLine.arguments[1])
let shots = root.appendingPathComponent("docs/screenshots")
let output = root.appendingPathComponent("docs/portfolio/assets")

func image(_ name: String) -> NSImage {
    guard let value = NSImage(contentsOf: shots.appendingPathComponent(name)) else {
        fatalError("Unable to load \(name)")
    }
    return value
}

let home = image("01-pokedex-home.png")
let search = image("02-pokedex-search.png")
let detail = image("03-pokemon-detail.png")
let favorites = image("04-pokemon-favorites.png")
let ink = NSColor(calibratedRed: 0.055, green: 0.082, blue: 0.145, alpha: 1)
let muted = NSColor(calibratedRed: 0.38, green: 0.43, blue: 0.54, alpha: 1)
let accent = NSColor(calibratedRed: 0.93, green: 0.18, blue: 0.04, alpha: 1)
let paper = NSColor(calibratedRed: 0.965, green: 0.97, blue: 0.985, alpha: 1)

func text(_ value: String, in rect: NSRect, size: CGFloat, weight: NSFont.Weight = .regular, color: NSColor = ink, align: NSTextAlignment = .left) {
    let style = NSMutableParagraphStyle()
    style.alignment = align
    style.lineBreakMode = .byWordWrapping
    value.draw(in: rect, withAttributes: [
        .font: NSFont.systemFont(ofSize: size, weight: weight),
        .foregroundColor: color,
        .paragraphStyle: style
    ])
}

func roundedRect(_ rect: NSRect, radius: CGFloat, color: NSColor) {
    color.setFill()
    NSBezierPath(roundedRect: rect, xRadius: radius, yRadius: radius).fill()
}

func phone(_ source: NSImage, rect: NSRect) {
    NSGraphicsContext.saveGraphicsState()
    let shadow = NSShadow()
    shadow.shadowColor = NSColor.black.withAlphaComponent(0.18)
    shadow.shadowBlurRadius = 18
    shadow.shadowOffset = NSSize(width: 0, height: -8)
    shadow.set()
    roundedRect(rect.insetBy(dx: -8, dy: -8), radius: 35, color: .white)
    NSGraphicsContext.restoreGraphicsState()

    NSGraphicsContext.saveGraphicsState()
    NSBezierPath(roundedRect: rect, xRadius: 28, yRadius: 28).addClip()
    source.draw(in: rect, from: .zero, operation: .sourceOver, fraction: 1)
    NSGraphicsContext.restoreGraphicsState()
}

func arrow(from: NSPoint, to: NSPoint) {
    accent.setStroke()
    let p = NSBezierPath()
    p.lineWidth = 3
    p.move(to: from)
    p.line(to: to)
    p.stroke()
    let angle = atan2(to.y - from.y, to.x - from.x)
    let length: CGFloat = 10
    for delta in [CGFloat.pi * 0.82, -CGFloat.pi * 0.82] {
        let wing = NSBezierPath()
        wing.lineWidth = 3
        wing.move(to: to)
        wing.line(to: NSPoint(x: to.x + cos(angle + delta) * length, y: to.y + sin(angle + delta) * length))
        wing.stroke()
    }
}

func save(_ name: String, draw: () -> Void) {
    let canvas = NSImage(size: NSSize(width: 1000, height: 750))
    canvas.lockFocus()
    paper.setFill()
    NSRect(x: 0, y: 0, width: 1000, height: 750).fill()
    draw()
    canvas.unlockFocus()
    guard let tiff = canvas.tiffRepresentation,
          let rep = NSBitmapImageRep(data: tiff),
          let png = rep.representation(using: .png, properties: [:]) else { fatalError("PNG failed") }
    try! png.write(to: output.appendingPathComponent(name))
}

save("pokemon-app-cover-1000x750.png") {
    roundedRect(NSRect(x: 0, y: 0, width: 1000, height: 750), radius: 0, color: NSColor(calibratedWhite: 0.985, alpha: 1))
    roundedRect(NSRect(x: 55, y: 78, width: 390, height: 594), radius: 32, color: .white)
    roundedRect(NSRect(x: 82, y: 603, width: 54, height: 8), radius: 4, color: accent)
    text("Pokédex\nAndroid App", in: NSRect(x: 82, y: 430, width: 325, height: 155), size: 50, weight: .bold)
    text("A modern creature discovery experience built with Jetpack Compose.", in: NSRect(x: 82, y: 310, width: 310, height: 100), size: 24, color: muted)
    text("CLEAN ARCHITECTURE   •   OFFLINE FAVORITES", in: NSRect(x: 82, y: 170, width: 320, height: 60), size: 14, weight: .semibold, color: accent)
    phone(detail, rect: NSRect(x: 493, y: 82, width: 270, height: 585))
    phone(home, rect: NSRect(x: 746, y: 136, width: 205, height: 444))
}

save("pokemon-app-flow-1000x750.png") {
    text("Explore. Search. Understand.", in: NSRect(x: 55, y: 665, width: 890, height: 55), size: 38, weight: .bold, align: .center)
    text("A focused product flow from discovery to detailed stats.", in: NSRect(x: 55, y: 625, width: 890, height: 35), size: 20, color: muted, align: .center)
    phone(home, rect: NSRect(x: 70, y: 78, width: 245, height: 531))
    phone(search, rect: NSRect(x: 377, y: 78, width: 245, height: 531))
    phone(detail, rect: NSRect(x: 684, y: 78, width: 245, height: 531))
    for (label, x) in [("DISCOVER", 70.0), ("SEARCH", 377.0), ("DETAIL", 684.0)] {
        roundedRect(NSRect(x: x + 66, y: 38, width: 113, height: 28), radius: 14, color: accent)
        text(label, in: NSRect(x: x + 66, y: 44, width: 113, height: 16), size: 11, weight: .bold, color: .white, align: .center)
    }
}

save("pokemon-app-engineering-1000x750.png") {
    text("Built for clarity and maintainability", in: NSRect(x: 55, y: 665, width: 890, height: 55), size: 38, weight: .bold)
    text("A testable Android architecture with clear data boundaries.", in: NSRect(x: 55, y: 625, width: 890, height: 35), size: 20, color: muted)
    phone(favorites, rect: NSRect(x: 65, y: 67, width: 250, height: 542))
    let boxes: [(String, String, CGFloat)] = [
        ("UI", "Jetpack Compose", 510),
        ("STATE", "ViewModel", 385),
        ("DOMAIN", "Use Cases", 260),
        ("DATA", "PokeAPI + Room", 135)
    ]
    for (eyebrow, title, y) in boxes {
        roundedRect(NSRect(x: 470, y: y, width: 430, height: 92), radius: 22, color: .white)
        roundedRect(NSRect(x: 490, y: y + 22, width: 78, height: 48), radius: 16, color: accent.withAlphaComponent(0.12))
        text(eyebrow, in: NSRect(x: 490, y: y + 38, width: 78, height: 16), size: 11, weight: .bold, color: accent, align: .center)
        text(title, in: NSRect(x: 592, y: y + 29, width: 270, height: 35), size: 25, weight: .semibold)
    }
    arrow(from: NSPoint(x: 685, y: 505), to: NSPoint(x: 685, y: 485))
    arrow(from: NSPoint(x: 685, y: 380), to: NSPoint(x: 685, y: 360))
    arrow(from: NSPoint(x: 685, y: 255), to: NSPoint(x: 685, y: 235))
}

print("Generated portfolio composites in \(output.path)")
