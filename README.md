# ♟️ IntelliChess

> **"Deep Blue was not intelligent at all. It was as intelligent as your alarm clock, a very expensive one, a $10 million piece. But what these machines are good for is helping chess players recognize new play patterns and mathematics."**  
> — Garry Kasparov (2024)

A **bitboard-based chess move generator** built in Java, demonstrating high-performance move generation techniques and space-time optimization trade-offs.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)

---

## 🎯 Project Scope

**This is a research project** (MCA Minor Project, Due: Jan 31, 2026) focused on:

1. **Bitboard move generation** for 4 piece types
2. **Performance benchmarking**: Lookup tables vs real-time calculation
3. **Technical documentation** of optimization techniques

**NOT building:** Full chess game, AI opponent, or GUI

---

## ✅ Implemented Features

| Piece | Method | Status |
|-------|--------|--------|
| **Knight** | Precomputed lookup table | ✅ Complete |
| **King** | Precomputed lookup table | ✅ Complete |
| **Pawn** | Bitwise shift operations | ✅ Complete |
| **Rook** | Ray-based sliding attacks | 🚧 In Progress |

---

## 🧠 Technical Approach

### Bitboard Representation
- Each piece type stored as a 64-bit `long`
- Each bit represents a square on the 8×8 board
- Parallel processing: operate on all pieces simultaneously

### Optimization Techniques
- **Lookup Tables**: O(1) move generation for Knights/Kings
- **Ray Tables**: Precomputed directional rays for Rooks
- **Bitwise Operations**: Shift, AND, OR for move filtering

---

## 🚀 Running the Demo

```bash
# Compile all source files
javac -d out src/main/java/com/sharma/intellichess/**/*.java

# Run comprehensive tests
java -cp out com.sharma.intellichess.demo.Demo
```

## Expected Output
```bash
=== KNIGHT MOVE GENERATION TEST ===
Test 1: Knight on e4 (center) - ✅ PASS
Test 2: Knight on a1 (corner) - ✅ PASS
Test 3: Knight on a4 (edge) - ✅ PASS

=== KING MOVE GENERATION TEST ===
...
```

## Project Structure
```
intelliCHESS-java/
├── src/main/java/com/sharma/intellichess/
│   ├── bitboard/
│   │   ├── BitboardUtils.java    # Square conversion, visualization
│   │   └── Masks.java             # File/rank masks for edge detection
│   ├── movegen/
│   │   ├── KnightMoves.java      # Precomputed knight attacks
│   │   ├── KingMoves.java        # Precomputed king attacks
│   │   ├── PawnMoves.java        # Single/double push logic
│   │   └── RookRays.java         # Ray generation (in progress)
│   └── demo/
│       └── Demo.java              # Test suite
```

## 📊 Next Steps
 - Complete Rook ray attack generation
 - Implement benchmark framework
 - Run performance tests (10M iterations)
 - Document results in research paper
 - Create presentation slides

## 🛠️ Tech Stack
 - Language: Java 21 (OpenJDK)
 - Platform: macOS (M4 Mac Mini)
 - Paradigm: Pure bitboard operations, no external libraries
 - Testing: Manual test cases with visual output

## 📄 License
MIT License - see LICENSE file

## 👨‍💻 Author
Sanskar Sharma
MCA 3rd Semester | Aspiring Project Manager  
GitHub: @Sharma-Sanskar

Built with ☕ and bitwise operations  
EOF

